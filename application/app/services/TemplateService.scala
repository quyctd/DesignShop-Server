package services

import com.google.inject.Inject
import models.ProductTemplate
import play.api.libs.json.{ JsObject, JsString }

class TemplateService @Inject() (productTemplate: ProductTemplate) {
  def toJson(productTemplate: ProductTemplate): JsObject = JsObject(Seq(
    "product_template_id" -> JsString(productTemplate.id.toString),
    "width" -> JsString(productTemplate.width.toString),
    "height" -> JsString(productTemplate.height.toString),
    "url" -> JsString(productTemplate.url),
    "right" -> JsString(productTemplate.right.toString),
    "left" -> JsString(productTemplate.left.toString),
    "top" -> JsString(productTemplate.top.toString),
    "bottom" -> JsString(productTemplate.bottom.toString)))
}
