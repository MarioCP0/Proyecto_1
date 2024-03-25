- Command for running a lisp file

```bash
  java -cp target\classes com.LispInterpreter <path-to-file>
```
#NOTE: podria estar ya compilado y todo, pero ZZZZZ

### Evaluator
- **HashMaps**:
  - `functions`: Almacena funciones.
  - `variables`: Almacena variables.
  - `TimeVariableHaveBeenSet`: Almacena información sobre el tiempo de asignación de variables.
  - `functionVariables`: Almacena parámetros de funciones.
- **ArrayList**:
  - Almacena los hijos de un nodo AST durante la evaluación de expresiones.

### ParserEnv
- **HashMaps**:
  - `Functions`: Almacena funciones.
  - `Variables`: Almacena variables.
  - `NestedLists`: Almacena listas anidadas.
- **LinkedList**:
  - `LogicalOrder`: Almacena el orden lógico de las operaciones y las expresiones condicionales.

### AST
- **ArrayList**:
  - Almacena los hijos de un nodo en el árbol sintáctico abstracto (AST).

### Node
- **ArrayList**:
  - Almacena los hijos de un nodo en el árbol sintáctico abstracto (AST).

### LispReader
- **ArrayList**:
  - Almacena los tokens extraídos de un archivo Lisp.

### LispTokenizer
- **HashMap**:
  - `expressionMap`: Mapea expresiones a identificadores únicos durante la tokenización.
- **ArrayList**:
  - `expressionList`: Almacena la lista de expresiones tokenizadas.

### LispInterpreter
- **ArrayList**:
  - Almacena la lista de expresiones tokenizadas.
- **HashMap**:
  - Almacena funciones y variables durante la interpretación.

Estas estructuras de datos se seleccionaron por su eficiencia en términos de acceso, inserción y eliminación de elementos, así como por su capacidad para representar de manera clara y precisa los datos necesarios en cada clase.
