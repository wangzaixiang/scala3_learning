package mcr1

import scala.quoted.*

inline def  assert(inline expr: Boolean): Unit =
    ${ assertImpl('expr) }


def assertImpl(expr: Expr[Boolean])(using Quotes): Expr[Unit] = '{
    if ! $expr then
        val msg = ${ Mcr().showExpr( expr ) }
        throw new AssertionError(s"failed assertion: $msg")

}

def to[T: Type, R: Type](f: Expr[T]=>Expr[R])(using Quotes): Expr[T=>R] =
    '{ (x:T) => ${ f('x) } }
def from[T: Type, R: Type](f: Expr[T=>R])(using Quotes): Expr[T] => Expr[R] =
    (x: Expr[T]) => '{ $f( $x) }

//
class Mcr {

    def showExpr(value: Expr[Boolean])(using x: Quotes): Expr[String] = {
        import x.reflect.*  // import quotes.reflect.*
        println("helllo")
        val term = value.asTerm     //
        val tpe: TypeRepr = term.tpe
        val _type = tpe.asType  // TypeImpl

        println(s"here inside macro $term") //
        val str = value.show
        Expr(str)
    }
}