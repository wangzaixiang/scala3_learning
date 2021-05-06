package mcr2

import scala.quoted.*

// We have a Term, an Expr and a String name
// 1. Call the field with the given String name on Expr
// 2. Pass the result to a method called on a Term
// 3. Make the types of the result of (1) and the input to (2) generic
// 4. Call the method without knowing the generic type on compile time
class Show[T](value: T):
    def field: T = value
    def show[T](t: T): String = s"The value is $t"

inline def mcr(expr: Show[?], inline name: String): Any =
    ${ Show.mcrImpl('expr, 'name) }

object Show {
    def mcrImpl(expr: Expr[Show[?]], nameExpr: Expr[String])(using q: Quotes): Expr[Any] =
        import q.reflect.*

        println("inside Show.mcrImpl")

        // Select field by name – use '{$expr.field}.asTerm to see what tree needs to be created
        val exprTree: Term = expr.asTerm
        val name: String = Expr.unapply(nameExpr).get
        val field: Term = Select.unique(exprTree, name)

        val tpe: TypeRepr = field.tpe.widen
        val a: q.reflect.Select = Select.unique(exprTree, "show")
        val b: q.reflect.Tree  = a.appliedToType(tpe)

        // Pass the result to a method call – same technique
        val showWithType = Select.unique(exprTree, "show").appliedToType(field.tpe.widen)
        val call = Apply(showWithType, field :: Nil).asExpr
        println(call.show)
        call

}