package tests

import common.BaseTest
import io.restassured.RestAssured
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasKey
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import utils.Utils

@RunWith(JUnit4::class)
class UsersTests : BaseTest() {

    companion object {
        const val USER_API_URL = "$BASE_API_URL/api/users"
        const val REQUEST_PATH = "request/user"
    }

    @Test
    fun successGetUsersFromPage() {
        RestAssured.given()
            .`when`()
            .queryParam("page", 2)
            .get(USER_API_URL)
            .then()
            .assertThat()
            .statusCode(200)
            .and()
            .body("page", `is`(2))
            .body("per_page", `is`(6))
            .body("total", `is`(12))
            .body("total_pages", `is`(2))
            .body("data.size()", `is`(6))
            .body("data[0]", hasKey("id"))
            .body("data[0]", hasKey("email"))
            .body("data[0]", hasKey("first_name"))
            .body("data[0]", hasKey("last_name"))
            .body("data[0]", hasKey("avatar"))
            .body("ad.company", `is`("StatusCode Weekly"))
            .body("ad.url", `is`("http://statuscode.org/"))
            .body(
                "ad.text",
                `is`("A weekly newsletter focusing on software development, infrastructure, the server, performance, and the stack end of things.")
            )
    }

    @Test
    fun successGetSingleUser() {
        RestAssured.`when`()
            .get("$USER_API_URL/2")
            .then()
            .assertThat()
            .statusCode(200)
            .and()
            .body("data.id", `is`(2))
            .body("data.email", `is`("janet.weaver@reqres.in"))
            .body("data.first_name", `is`("Janet"))
            .body("data.last_name", `is`("Weaver"))
            .body("data.avatar", `is`("https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg"))
            .body("ad.company", `is`("StatusCode Weekly"))
            .body("ad.url", `is`("http://statuscode.org/"))
            .body(
                "ad.text",
                `is`("A weekly newsletter focusing on software development, infrastructure, the server, performance, and the stack end of things.")
            )
    }

    @Test
    fun notFoundGetSingleUser() {
        RestAssured.`when`()
            .get("$USER_API_URL/23")
            .then()
            .assertThat()
            .statusCode(404)
            .and()
            .body("isEmpty()", `is`(true))
    }

    @Test
    fun updateUserFromPatch() {
        val request = Utils.getResourcesFile("request/user/update_user.json").bufferedReader().use { it.readText() }

        RestAssured.given()
            .contentType("application/json")
            .body(request)
            .`when`().patch("$USER_API_URL/2")
            .then()
            .assertThat()
            .statusCode(200)
            .and()
            .body("name", `is`("morpheus"))
            .body("job", `is`("zion resident"))
            .body("$", hasKey("updatedAt"))
    }

    @Test
    fun updateUserFromPut() {
        val request = Utils.getResourcesFile("$REQUEST_PATH/update_user.json").bufferedReader().use { it.readText() }

        RestAssured.given()
            .contentType("application/json")
            .body(request)
            .`when`().put("$USER_API_URL/2")
            .then()
            .assertThat()
            .statusCode(200)
            .and()
            .body("name", `is`("morpheus"))
            .body("job", `is`("zion resident"))
            .body("$", hasKey("updatedAt"))
    }

}