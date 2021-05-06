package demo
import scala.collection.mutable

trait Library {

    type Elem

    extension (e: Elem) {
        def show(label: String): Unit = println(s"$label $e")
    }

    def add(elem: Elem): Unit
    def list(): List[Elem]

}

case class Book(name: String)

class BookLibrary extends Library {

    override type Elem = Book
    val elements = collection.mutable.ListBuffer[Elem]()

    override def add(elem: Elem): Unit = {
        elements.append(elem)
        elem.show("add")
    }

    override def list(): List[Elem] = elements.toList
}

@main
def test(): Unit ={
    val lib = new BookLibrary
    import lib.{given, *}
    val book = new lib.Elem("java")
    book.show("created")
        lib.add(book)
    val x: Seq[Book] = lib.list()
    println(x)
}
