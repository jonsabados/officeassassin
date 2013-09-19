package com.jshnd.assassin.rest.validation

import org.apache.bval.jsr303.DefaultMessageInterpolator
import javax.validation.MessageInterpolator.Context
import com.google.inject.servlet.RequestScoped
import javax.servlet.http.HttpServletRequest
import com.google.inject.{Singleton => GSingleton, Provider, Binder, Module, Inject}
import javax.validation.MessageInterpolator
import java.util.Locale

class LocaleAwareModule extends Module {
  def configure(binder: Binder) {
    binder.bind(classOf[MessageInterpolator]).to(classOf[LocaleAwareMessageInterpolator]).in(classOf[GSingleton])
    binder.bind(classOf[Locale]).toProvider(classOf[LocaleProvider])
  }
}

@RequestScoped
class LocaleProvider @Inject() (request: HttpServletRequest) extends Provider[Locale] {
  def get(): Locale = request.getLocale
}

class LocaleAwareMessageInterpolator @Inject() (localeProvider: Provider[Locale]) extends DefaultMessageInterpolator {
  override def interpolate(message: String, context: Context): String = interpolate(message, context, localeProvider.get())
}
