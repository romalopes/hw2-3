MongoDB Course. Home work 
=============================

GitHub on
https://github.com/romalopes/hw2-3

- MongoDB Course
	Info:
		https://education.mongodb.com/courses
		Lenght of course: - 7 weeks
	Using Maven
		to run the project: mvn compile exec:java -Dexec.mainClass=course.BlogController
		to test: mvn test
	Using Gradle
	    to rum the project: gradle run
	    to test: gradle test
	
	To create a project with gradle
		- copy the directory Gradle to root
			- gradle
				wrapper
					gradle-wrapper.jar
					gradle-wrapper.properties
				ide.gradle
		- copy the btch file to root
			gradlew (for X)
			gradle.bat ( for windows)
		- copy settings.gradle to root
			Only this inside: rootProject.name = "NAME PROJECT"

	- Week 1
		- Suports
			Based on Json Documents(key, values). It is a document database
			In same document Dynamic Schema.  Different collections of data.
		- Scalability and performance VS Funcionality
			Doesn't support joins and transactions. Documents are hierarchical
		- Running
			create a directory %MONGO_HOME%\data\db
			- %MONGO_HOME%/bin/mongo.config
				##store data here
				dbpath=%MONGO_HOME%\data\db
				##all output go here
				logpath=%MONGO_HOME%\log\mongo.log
				##log read and write operations
				diaglog=3

			- Server:
				mongod --dbpath data\db
				mongod --config %MONGO_HOME%\bin\mongo.config
				As a server
					mongod --config %MONGO_HOME%\bin\mongo.config --install
					net start MongoDB
					net stop MongoDB
					mongod --remove (remove the service)
			- Command line: mongo

				Ex:
					mongo test(connects to mongodb already in test collection)
					use test (switches to test collection)
					show collections
					   (collection)  (command)
					db.objects.      save({a:1, b:2, c:3})
					db.objects.      save({a:4, b:5, c:6, d:7, e:['a','b']})
					db.objects.find() <- return all documents
					db.objects.find({a:1}) <- Return all documents that have this element

					db.course.hello.save({'name','maths'})
					db.course.hello.find()

		- Json
			Types of Data
				- Array -> list of items	-> [...]
				- Dictionaries ->associative maps  {key:value} -> {name:"value",city:"value", interests:[ ___, ___, ___]
		- Embeded data into documents
			If the embeded data exceed 16Mb, because each document should have the maximum of 16Mb
		Home Work:
			1 - 42
			2- 2,3,5
			3-366
			4- 2805
	- Week 2
		- CRUD
			Create=Insert.  Read=Find.  Update=Update.  Delete=Remove
			Does not have a language such as SQL.
			- db.people.insert( doc )
			- db.people.find()
			- db.people.findOne({"name": "Anderson"}, {"name":true,"_id":false})
			- all object has a primary key called (_id)
		- BSON - Binary JSON Object
		- Query
			- $gt, $lt,
				- db.people.findOne({name:{$gt:	"B", $lt:"D"}})
				- db.people.findOne({name:{$gt:	"B"}, {name:{$lt:"D"}} }) --Will return all docs less than D. Ignore the first statement.
			- exists
				- To verify if a field exists
				- db.people.findOne({name:{ $exists : true}})  -- return all documents that has the field name.
			- regex
				- db.people.find({name: { $regex : "^A"}}) -> returns everything that starts with A.
				ex:db.users.find( {name: {$regex:"q"}, email: {$exists:true} } )
			- or											//ends with e
				- db.people.find( { $or : [ {name: { $regex : "e$" } } , {age : {$exists: true}]})
				- db.scores.find( {$or : [ {score : {$lt: 50}}, {score : {$gt: 90}} ]})
			- and
			- all - return the documents that have all the specified elements in a array
				db.accounts.find( { favorites : { $all, [ "a", "b", "c" ] } } )
			- in - return all documents that has on of the objects IN a array
				db.accounts.find( { name : { $in, [ "Anderson", "Cida"] } } ) -- returns the docs that have Anderson and Cida
			- Queries
				db.users.find( {email : {work: "romalopes@yahoo.com.br" } } )
				or
				db.users.find( {email.work: "romalopes@yahoo.com.br" } )
			- Cursor

				db.people.insert( {name: "Anderson", email : {work:"111", home:"222"}});
				db.people.insert( {name: "Cida", email : {work:"111", home:"222"}});
				- cur = db.people.find(); null; //NULL avoid the return.
				- cur.limit(5); null; get only 5 elements.
				- cur.sort( {name: -1 }); null;
				- cur.sort( {name: -1 }).limit(5); null;
				- while(cur.hasNext()) printjson(cur.next());

				- db.scores.find( { type : "exam" } ).sort( { score : -1 } ).skip(50).limit(20)
			- count
				db.score.count({type:"exam"})
				- db.scores.count( {"type":"essay", score: {$gt: 90 } } )
			- Update
				- db.people.update( { name: "Anderson"} , {name:"Anderson Lopes", address:"114 Hargrave"})
					- Creates, remove and replace all the attributes.
				- $set
					db.people.update( { name: "Anderson"} , {$set: {age:30}} )
						- set only the values we want to change.
					db.people.update( { name: "Anderson"} , {$inc: {age:3} } )
						- increment 3 in age.
					- ex:
						db.arrays.insert( {_id:0, a:[1,2,3,4]});
						db.arrays.update( {_id:0, {$set : {"a.2" : 5 }});
							change the third element from 3 to 5.
						db.arrays.update( {_id:0, {$push : {a : 6 }});
							add at the end of array
						db.arrays.update( {_id:0, {$pop : {a : 1 }}); //remove the last element
						db.arrays.update( {_id:0, {$pop : {a : -1 }}); //remove the first element
						db.arrays.update( {_id:0, {$pushAll : {a : [6, 7, 8, 9 }});
						db.arrays.update( {_id:0, {$pull : {a : 3} }); //remove the element that the value is 3
						db.arrays.update( {_id:0, {$pullAll : {a : [6, 7, 8, 9 }}); // remove all elements with the values in the second array

				- $unset (remove a field from a collection)
					db.people.update( { name: "Anderson"} , {$unset: {age:1} } )
			- Multi-update
				Update multiple documents
				db.people.update( {} , {$set :{"title": "Dr" } }, {multi: true} );
				db.scores.update ( { score: {$lt:70} }, {$inc: {score:20}}, {multi:true});
			- Remove a database
				- use DATABASE
				- db.dropDatabase()
			- remove
				db.people.remove() //remove everything from the collection.
				db.people.drop() //faster than remove
				db.people.remove( {name:"Alice"})
				db.people.remove( {name: {$gt: "M" }} )
				db.scores.remove( {score: { $lt: 60 }})
			- db.runCommand( {getLastError:1})
				return the last result of a command.  It can be a error, a result of a update and so on.
		With Java
			CRUD
				INSERT DBOBject(interface) . BasicDBObject with is a LinkedHashMap<String, Object>
				 BasicDBObject doc = new BasicDBObject();
					doc.put("userName", "anderson");
					doc.put("birthDate", new Date(2344242));
					doc.put("programmer", true);
					doc.put("age", 8);
					doc.put("languages", Arrays.asList("Java", "C++"));
					doc.put("address", new BasicDBObject("street", "114 Hargrave")
								.append("town", "Paddington")
								.append("zipCode", 2021));


					{  "_id" : "user1",
					   "interests" : [ "basketball", "drumming"]	}
					new BasicDBObject("_id", "user1").append("interests", Arrays.asList("basketball", "drumming"));
				FIND
			        DB courseDB = client.getDB("course");
					DBCollection collection  = courseDB.getCollection("findCriteriaTest");

					collection.drop();

					for(int i=0; i<10; i++) {
						collection.insert(new BasicDBObject( "x", new Random().nextInt(2)).
														   append("y", new Random().nextInt(100)));
					}

					System.out.println("\n Find All");
					DBCursor cursor1 = collection.find();
					try {
						while(cursor1.hasNext()) {
							DBObject all = cursor1.next();
							System.out.println(all);
					   }
					} finally {
						cursor1.close();
					}

					DBObject query = new BasicDBObject("x", 0)
																		//AND in Y
									.append("y", new BasicDBObject("$gt", 10).append("$lt", 90));
	//OR
					QueryBuilder builder = QueryBuilder.start("x").is(0)
							.and("y").greaterThan(10).lessThan(90);


					System.out.println("\n Count");
					long count = collection.count(query); // builder.get()
					System.out.println(count);

					System.out.println("Find One");
					DBObject one = collection.findOne(query); //builder.get()
					System.out.println(one);

					System.out.println("\n Find All");
					DBCursor cursor = collection.find(query); //builder.get()
					try {
						while(cursor.hasNext()) {
							DBObject all = cursor.next();
							System.out.println(all);
						}
					} finally {
						cursor.close();
					}

					for(int i=0; i<10; i++) {
						collection.insert(new BasicDBObject( "_id", i).
                           append("start",
                                   new BasicDBObject("x", new Random().nextInt(90) + 10)
                                                .append("z", new Random().nextInt(90) + 10)
                           ).append("end",
                                new BasicDBObject("x", new Random().nextInt(90) + 10)
                                        .append("z", new Random().nextInt(90) + 10)
                                )
						);
					}

					//QueryBuilder builder = QueryBuilder.start("start.x").greaterThan(50);
					//DBCursor cursor = collection.find(builder.get(),
					//        new BasicDBObject("start.x", true).append("_id", false).append("end.z", true));

					DBCursor  cursor = collection.find()
								.sort(new BasicDBObject("_id", -1)).skip(5).limit(3);
					DBCursor  cursor = collection.find()
								.sort(new BasicDBObject("start.x", -1).append("end.z",1)).skip(5).limit(3);
				UPDATE
				REMOVE
															//query
					collection.update(new BasicDBObject("_id", "alice"),
							new BasicDBObject("age", 35));//update

					collection.update(new BasicDBObject("_id", "alice"),
							new BasicDBObject("gender", "Female"));//update
					//will remove the gender - Female and insert the Title Srs
					collection.update(new BasicDBObject("_id", "alice"),
							new BasicDBObject("$set" , new BasicDBObject("Title", "Srs")));//update

					//To insert, it is needed two last parameters
					collection.update(new BasicDBObject("_id", "frank"),                 //to insert
							new BasicDBObject("$set" , new BasicDBObject("Title", "Sr")), true, false);//update

					collection.update(new BasicDBObject(),//every document                //to update
							new BasicDBObject("$set" , new BasicDBObject("Graduation", "Masters")), false, true);//update

					//collection.remove(new BasicDBObject());
					collection.remove(new BasicDBObject("_id", "alice"));
		- About blog
			View - ftl - freemarker
			controller/model java/spark.

		- Home Work -
			1 - db.grades.find({score:{$gte:65}}).sort( { score : 1 } ).limit(1)
			2 - 124
			3 - fhj837hf9376hgf93hf832jf9
	- Week 3
		- MongoDB Schema Design
			Application-Driven Schema
			. Features
				Rich Documents
				Pre join data for fast access
				No Joing
				No Constraina like MySql
				Atomic operations(no transactions)
				No Declared Schema