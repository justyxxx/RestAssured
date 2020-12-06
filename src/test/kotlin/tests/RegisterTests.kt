package tests

import common.BaseTest
import io.restassured.RestAssured
import org.hamcrest.Matchers.`is`
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import utils.Utils


@RunWith(JUnit4::class)
class RegisterTests : BaseTest() {

    companion object {
        const val REGISTER_API_URL = "$BASE_API_URL/api/register"
        const val REQUEST_PATH = "request/register"

    }

    @Test
    fun successRegister() {
        val request =
            Utils.getResourcesFile("$REQUEST_PATH/succes_register.json").bufferedReader().use { it.readText() }

        RestAssured.given()
            .contentType("application/json")
            .body(request)
            .`when`().post(REGISTER_API_URL)
            .then()
            .assertThat()
            .statusCode(200)
            .and()
            .body("id", `is`(4))
            .body("token", `is`("QpwL5tke4Pnpja7X4"))
    }

    @Test
    fun unSuccessRegisterMissPassword() {
        val request =
            Utils.getResourcesFile("$REQUEST_PATH/un_succes_register.json").bufferedReader().use { it.readText() }

        RestAssured.given()
            .contentType("application/json")
            .body(request)
            .`when`().post(REGISTER_API_URL)
            .then()
            .assertThat()
            .statusCode(400)
            .and()
            .body("error", `is`("Missing password"))
    }

    @Test
    fun unSuccessRegisterMissEmail() {
        val request =
            Utils.getResourcesFile("$REQUEST_PATH/un_succes_register_miss_email.json").bufferedReader().use { it.readText() }

        RestAssured.given()
            .contentType("application/json")
            .body(request)
            .`when`().post(REGISTER_API_URL)
            .then()
            .assertThat()
            .statusCode(400)
            .and()
            .body("error", `is`("Missing email or username"))
    }

    @Test
    fun unSuccessRegisterMissAll(){
        val request = "";
        RestAssured.given()
            .contentType("application/json")
            .body(request)
            .`when`().post(REGISTER_API_URL)
            .then()
            .assertThat()
            .statusCode(400)
            .and()
            .body("error", `is`("Missing email or username"))
    }


}