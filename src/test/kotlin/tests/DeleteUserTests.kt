package tests

import common.BaseTest
import io.restassured.RestAssured
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4



@RunWith(JUnit4::class)
class DeleteUserTests : BaseTest() {
    companion object {
        const val REGISTER_API_URL = "$BASE_API_URL/api/users/2"
    }

    @Test
    fun deleteUser() {

        RestAssured.given()
            .contentType("application/json")
            .`when`().delete(DeleteUserTests.REGISTER_API_URL)
            .then()
            .assertThat()
            .statusCode(204)
    }
}