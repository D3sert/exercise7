# Aufgabenblatt 8

## Tasks

1. Static program understanding:
    - Add the preconditions (V) and postconditions (N) for the methods in the interface
      Add `IndexTreeNode` in the comments.
    - Add assertions as Hoare triples `{V} S {N}` at the appropriate places in
      of the class `CosmicSystemIndexTree`. `S` should be an instruction that
      contains a method call to `IndexTreeNode`.
      You can include the appropriate assurances as comments, or - if the
      Express assurances in Java - corresponding `assert` statements.
    - Determine a loop invariant for each of the loops in the `add` method
      Class `CosmicSystemIndexTree`. Add comments or where appropriate
      `assert` statement.
2. Assertions and sub-types:
    - Add the preconditions and postconditions for the methods in the classes
      `IndexTreeNullNode` and` IndexTreeNonNullNode` as comments for the methods.
    - Check whether the conditions for the formation described in the script on page 142
      are met by sub-types and, if necessary, indicate errors in the comments.
3. Change your class `ComplexCosmicSystem` as follows: Define an interface
   `ComplexNode` as a common supertype for` ComplexNullNode` and `ComplexNonNullNode`
   and implement it in these classes. Think about it accordingly
   Assurances and use them to code the `ComplexCosmicSystem`
   simplify. Use the class `CosmicSystemIndexTree` as a template.
   (There the given interface `IndexTreeNode` is a supertype for` IndexTreeNullNode` and
   `IndexTreeNonNullNode`).

 