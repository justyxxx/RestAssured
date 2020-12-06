package tests

import common.BaseTest
import io.restassured.RestAssured
import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResourcesTests : BaseTest() {

    companion object {
        const val UNKNOWN_API_URL = "$BASE_API_URL/api/unknown"
    }

    @Test
    fun successGetListResources() {
        RestAssured.`when`()
            .get(UNKNOWN_API_URL)
            .then()
            .assertThat()
            .statusCode(200)
            .and()
            .body("page", `is`(1))
            .body("per_page", `is`(6))
            .body("total", `is`(12))
            .body("total_pages", `is`(2))
            .body("data.size()", `is`(6))
            .body("data[0]", Matchers.hasKey("id"))
            .body("data[0]", Matchers.hasKey("name"))
            .body("data[0]", Matchers.hasKey("year"))
            .body("data[0]", Matchers.hasKey("color"))
            .body("data[0]", Matchers.hasKey("pantone_value"))
            .body("ad.company", `is`("StatusCode Weekly"))
            .body("ad.url", `is`("http://statuscode.org/"))
            .body(
                "ad.text",
                `is`("A weekly newsletter focusing on software development, infrastructure, the server, performance, and the stack end of things.")
            )
    }

    @Test
    fun successGetSingleResource() {
        RestAssured.`when`()
            .get("$UNKNOWN_API_URL/2")
            .then()
            .assertThat()
            .statusCode(200)
            .and()
            .body("data.id", `is`(2))
            .body("data.name", `is`("fuchsia rose"))
            .body("data.year", `is`(2001))
            .body("data.color", `is`("#C74375"))
            .body("data.pantone_value", `is`("17-2031"))
            .body("ad.company", `is`("StatusCode Weekly"))
            .body("ad.url", `is`("http://statuscode.org/"))
            .body(
                "ad.text",
                `is`("A weekly newsletter focusing on software development, infrastructure, the server, performance, and the stack end of things.")
            )
    }

    @Test
    fun notFoundGetSingleResource() {
        RestAssured.`when`()
            .get("$UNKNOWN_API_URL/23")
            .then()
            .assertThat()
            .statusCode(404)
            .and()
            .body("isEmpty()", `is`(true))
    }
}