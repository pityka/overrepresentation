import org.scalatest.funsuite.AnyFunSuite
import scala.io.Source

class OverrepresentationTestSuite extends AnyFunSuite {

  test("trivia") {
    assertResult(overrepresentation.enrichmentTest(10000, 2000, 300, 60))(
      0.523725504101762
    )
  }

}
