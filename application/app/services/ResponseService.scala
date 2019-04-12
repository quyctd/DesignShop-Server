package services

import play.api.libs.json._

object ResponseService {

  def success(message: String = "Success", data: Seq[JsObject] = Seq(), returnEmptyData: Boolean = false): JsObject = {
    var responseJs = JsObject(Seq(
      // format: OFF
      "code" -> JsNumber(200),
      "message" -> JsString(message)))
    // format: ON

    (returnEmptyData, data.isEmpty) match {
      case (false, true) =>
      case _ => responseJs += ("data", JsArray(data))
    }

    responseJs
  }

  def success(data: JsValue): JsObject =
    JsObject(Seq(
      // format: OFF
      "code" -> JsNumber(200),
      "message" -> JsString("Success"),
      "data" -> data))

  def badRequest(errorKey: String, errorMessage: String): JsObject = {
    val errorJs = Some(JsObject(Seq(
      errorKey -> JsArray(Array(JsString(errorMessage))))))

    badRequest(errorJs)
  }

  def badRequest(error: Option[JsValue], message: String = "Bad request"): JsObject = {
    var responseJs = JsObject(Seq(
      "code" -> JsNumber(400),
      "message" -> JsString(message)))
    // format: ON
    if (error.nonEmpty)
      responseJs += ("error", error.get)

    responseJs
  }

  def serverError(message: String): JsObject = JsObject(Seq(
    // format: OFF
    "code" -> JsNumber(500),
    "message" -> JsString(message)))

  def unAuthorized(message: String = "Unauthorized", errorCode: String = "", data: JsValue = JsObject(Seq())): JsObject = JsObject(Seq(
    "code" -> JsNumber(401),
    "message" -> JsString(message),
    "errorCode" -> JsString(errorCode),
    "data" -> data))

  def notFound(message: String = "Not found", errorCode: String): JsObject = JsObject(Seq(
    "code" -> JsNumber(404),
    "message" -> JsString(message),
    "errorCode" -> JsString(errorCode)))

  def forbidden(message: String = "Forbidden", errorCode: String, data: JsValue = JsObject(Seq())): JsObject = JsObject(Seq(
    "code" -> JsNumber(403),
    "message" -> JsString(message),
    "errorCode" -> JsString(errorCode),
    "data" -> data
  ))

  def internalServerError(message: String = "Internal server error", errorCode: String = ""): JsObject = JsObject(Seq(
    "code" -> JsNumber(500),
    "message" -> JsString(message),
    "errorCode" -> JsString(errorCode)))

  // format: ON
}
