import org.scalatest.FunSuite
import scala.io.Source

class OverrepresentationTestSuite extends FunSuite {

  test("trivia") {
    assertResult(overrepresentation.enrichmentTest(10000, 2000, 300, 60))(
      0.523725504101762)
  }

}
