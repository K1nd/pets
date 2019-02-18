package ltd.k1nd.pets.bones.log

import cats.effect.Sync
import cats.mtl.ApplicativeAsk
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.syntax.applicative._
import cats.syntax.apply._
import ltd.k1nd.pets.bones.deconstruct.ContextDeconstructor
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import sourcecode.Enclosing

import scala.collection.JavaConverters._

object MdcLogger {
  def apply[O[_]: Sync,
            F[_]: Sync,
            Ctx: ApplicativeAsk[F, ?]: ContextDeconstructor](
      forClass: Class[T] forSome {
        type T
      }): O[Logger[F]] =
    for {
      underlyingLogger <- Sync[O].delay(LoggerFactory.getLogger(forClass))
    } yield
      new Logger[F] {
        override def trace(msg: String)(implicit path: Enclosing): F[Unit] =
          trace(msg.pure[F])
        override def debug(msg: String)(implicit path: Enclosing): F[Unit] =
          debug(msg.pure[F])
        override def info(msg: String)(implicit path: Enclosing): F[Unit] =
          info(msg.pure[F])
        override def warn(msg: String)(implicit path: Enclosing): F[Unit] =
          warn(msg.pure[F])
        override def error(msg: String)(implicit path: Enclosing): F[Unit] =
          error(msg.pure[F])
        override def error(msg: String, ex: Throwable)(
            implicit path: Enclosing): F[Unit] =
          error(msg.pure[F], ex.pure[F])

        override def trace(msg: F[String])(implicit path: Enclosing): F[Unit] =
          for {
            traceEnabled <- Sync[F].delay(underlyingLogger.isTraceEnabled())
            _ = ApplicativeAsk[F, Ctx].ask
            logF = setMdc *> msg.flatMap(m =>
              Sync[F].delay(underlyingLogger.trace(m)))
            _ <- logF.whenA(traceEnabled)
          } yield ()

        override def debug(msg: F[String])(implicit path: Enclosing): F[Unit] =
          for {
            debugEnabled <- Sync[F].delay(underlyingLogger.isDebugEnabled())
            logF = setMdc *> msg.flatMap(m =>
              Sync[F].delay(underlyingLogger.debug(m)))
            _ <- logF.whenA(debugEnabled)
          } yield ()

        override def info(msg: F[String])(implicit path: Enclosing): F[Unit] =
          for {
            infoEnabled <- Sync[F].delay(underlyingLogger.isInfoEnabled())
            logF = setMdc *> msg.flatMap(m =>
              Sync[F].delay(underlyingLogger.info(m)))
            _ <- logF.whenA(infoEnabled)
          } yield ()

        override def warn(msg: F[String])(implicit path: Enclosing): F[Unit] =
          for {
            warnEnabled <- Sync[F].delay(underlyingLogger.isWarnEnabled())
            logF = setMdc *> msg.flatMap(m =>
              Sync[F].delay(underlyingLogger.warn(m)))
            _ <- logF.whenA(warnEnabled)
          } yield ()

        override def error(msg: F[String])(implicit path: Enclosing): F[Unit] =
          for {
            errorEnabled <- Sync[F].delay(underlyingLogger.isErrorEnabled())
            logF = setMdc *> msg.flatMap(m =>
              Sync[F].delay(underlyingLogger.error(m)))
            _ <- logF.whenA(errorEnabled)
          } yield ()

        override def error(msg: F[String], ex: F[Throwable])(
            implicit path: Enclosing): F[Unit] =
          for {
            errorEnabled <- Sync[F].delay(underlyingLogger.isErrorEnabled())
            logF = setMdc *> (msg, ex)
              .mapN((m, e) => Sync[F].delay(underlyingLogger.error(m, e)))
              .flatten
            _ <- logF.whenA(errorEnabled)
          } yield ()

        private def setMdc(implicit path: Enclosing): F[Unit] = {
          for {
            context <- ApplicativeAsk[F, Ctx].ask
            extractedValues = ContextDeconstructor[Ctx].deconstruct(context)
            valuesWithFixedKeys = extractedValues.map {
              case (keys, value) => keys.mkString(".") -> value
            } + ("CodeLocation" -> path.value)
            _ <- Sync[F].delay(MDC.setContextMap(valuesWithFixedKeys.asJava))
          } yield ()
        }
      }
}
