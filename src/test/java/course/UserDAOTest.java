package course;

import com.mongodb.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Unit test for simple App.
 */
public class UserDAOTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public UserDAOTest(String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( UserDAOTest.class );
    }

    public void testAddUserUsingOnlyDB() throws UnknownHostException {

        assertTrue( true );
        MongoClient client = new MongoClient();

        String username="AddUserUsingOnlyDB";
        String password = "lopes";
        String email="romalopes@yahoo.com.br";

            // XXX WORK HERE
// create an object suitable for insertion into the user collection
// be sure to add username and hashed password to the document. problem instructions
// will tell you the schema that the documents must follow.
        final DB blogDatabase = client.getDB("blog");
        DBCollection usersCollection = blogDatabase.getCollection("users");
        BasicDBObject user = new BasicDBObject( "_id", username).
                append("password", password);
        usersCollection.insert(user);

//        usersCollection.update(new BasicDBObject( "username", username),
//                new BasicDBObject( "email",email));
        usersCollection.update(new BasicDBObject("_id", username),
                new BasicDBObject("$set" , new BasicDBObject("email", email)), true, false);


        DBObject query = new BasicDBObject("_id", username);
        DBObject userResult = usersCollection.findOne(query);

        System.out.println("userResult :" +userResult );

        assert(userResult.get("_id").equals(username));
//        assert(userResult.get("password").equals(password));
        assert(userResult.get("email").equals(email));

        usersCollection.remove(userResult);
    }

    public void testAddUserCallingDAO() throws UnknownHostException {

        assertTrue( true );
        MongoClient client = new MongoClient();

        String username="AddUserCallingDAO";
        String password = "lopes";
        String email="romalopes@yahoo.com.br";

        // XXX WORK HERE
// create an object suitable for insertion into the user collection
// be sure to add username and hashed password to the document. problem instructions
// will tell you the schema that the documents must follow.
        final DB blogDatabase = client.getDB("blog");

        UserDAO userDAO = new UserDAO(blogDatabase);
        userDAO.addUser(username, password,email);

        DBCollection usersCollection = blogDatabase.getCollection("users");
        DBObject query = new BasicDBObject("_id", "AddUserCallingDAO");
        DBObject userResult = usersCollection.findOne(query);


        System.out.println(userResult );
        assertTrue(userResult.get("_id").equals(username));
        assert(userResult.get("email").equals(email));

        usersCollection.remove(userResult);

    }

    public void testAddUserWithRunningServer() throws IOException, InterruptedException {

        assertTrue( true );
        // Run a java app in a separate system process

        //Process procSpark = Runtime.getRuntime().exec("mvn exec:java -Dexec.mainClass=course.BlogController");
//        procSpark.wait(3000);
        Process proc = Runtime.getRuntime().exec("java -jar C:/workspace/linguagens/mongoDB/course/week2/hw2-3/Validate.jar");
        proc.waitFor();
        // Then retreive the process output
        InputStream in = proc.getInputStream();
        InputStream err = proc.getErrorStream();

        byte b[]=new byte[in.available()];
        in.read(b,0,b.length);
        String strIn = new String(b);
        System.out.println("in=" + strIn);

        byte c[]=new byte[err.available()];
        err.read(c, 0, c.length);
        String st = new String(c);
        System.out.println("err = " +st);

        assertFalse(strIn.contains("Exception"));
        assertFalse(strIn.contains("Sorry"));

        //procSpark.destroy();
    }

}
