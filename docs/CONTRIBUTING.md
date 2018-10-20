# Contributing Guidelines

The Hyperium-Kotlin (HKT) project appreciates all contributions to this open source repository.
However, for your work to meet our standards, you should follow these simple guidelines.

1. All code should be written in Kotlin. [See Mixins](#mixins)*

2. Follow the Kotlin Lang coding conventions found [here](https://kotlinlang.org/docs/reference/coding-conventions.html).

3. Follow all other documents provided in the `docs/` directory.

4. Provide clear and adequate commit messages.


### Mixins

Mixins are different from the rest of the project in that they should be written in Java. This is due to the fact
that the Mixin library does not provide proper support for Kotlin.

Every Mixin should be in a Java file by itself. A normal Mixin class should follow the naming convention of
the class they target prefixed with "Mixin." For example, a Mixin targeting World should be named
`MixinWorld`. All accessor Mixins should also be prefixed with a capital `I`. This would make the Mixin
class name `IMixinWorld` continuing our previous example.

In addition, all Accessor Mixins should provide extensions for their target class that provides a casted version
of that class. For example, an accessor Mixin targeting the `World` class should provide an extension function
for `World` that returns an `IMixinWorld` instance.