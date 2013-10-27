package course

/**
 * Created with IntelliJ IDEA.
 * User: Anderson
 * Date: 27/10/13
 * Time: 21:26
 * To change this template use File | Settings | File Templates.
 */
class UserDAOSpec extends spock.lang.Specification {
    def "First test of UserDAO"() {
        expect:
        name.size() == length

        where:
        name     | length
        "Spock"  | 5
        "Kirk"   | 4
        "Scotty" | 6
    }


}
