case class User(name:String)
case class Student(nmae: String)

@main def hello: Unit = {
    println("Hello world!")
    println(msg)
    
    val user = User("wzx")
    val student1 = Student("wzx")
    
    val same = user == student1
    println(s"same = $same")
}

def msg = "I was compiled by Scala 3. :)"
