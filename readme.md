# Ellens solution to Gilded Rose

## Changes to setup

- [x] update to java 21
- [x] add assertj
- [ ] update gradle
- [x] mvn test passes
- [x] ./gradlew test passes
- [x] Intelij can run with test coverage

## Understanding, Analyzing, Testing, and Simple Refactoring of Existing Code

The first thing we need to do is understand the existing code. The requirements are not very detailed, leaving room for
interpretation.
Before refactoring, I added as many tests as possible and extended them during the refactoring process.

### At First Sight...

We see a shared list and items. The list and the value of the items can be changed externally. This is something that *
*scares me a lot**.
We should always strive to isolate our code as much as possible so we can easily add tests when a bug inevitably creeps
in (because it always does).

The list is iterated over using a `for` loop. Switching to a `foreach` loop improves readability instantly.

There are also **a lot** of nested `if` statements before and after the `sellIn` value is changed. With a bit of
thinking, this structure can be improved.

Additionally, we see three specific item names that do not match the requirements exactly. The requirements mention *'
backstage passes'*, but they are not specific about the concert. For now, we’ll keep things specific, but we should
remain flexible in case this needs to change.

---

### Tests

I added the `AssertJ` library, which makes it easier to write readable tests. I prefer using the **builder pattern** to
set up test data. This allows us to focus on the variables that matter for a specific case. However, if a variable is
used **during** the test, it should be explicitly specified within the test.

The test data in this example is not extensive, but we have **two integers**, which are very easy to accidentally
switch.

When dealing with a large number of tests, I like to structure them by topic using nested classes. From the original
code, I split the tests into three major categories, progressing from the easiest to understand to the most complex:

1. The weird construction with the list. Does every item remain present? What about duplicates?
2. How is `sellIn` updated?
3. The quality updates.
    - Further nested by item name/type.
    - If necessary, further grouped by `sellIn` value.
    - Behavior differs for positive and negative `sellIn`, so zero is explicitly tested.
    - Keeping zero tests separate for clarity, though merging them with negative tests **might** be an option.

There are always 'fun little debates' when it comes to test styling:

- **Naming Conventions:** I usually go with the `given_when_then` format, using underscores for readability. When names
  get too long, I omit part of `given` in nested classes. `when` is often obvious because most classes and functions are
  small. Of course, everyone has their own naming preferences, and consistency is hard even when working alone—let alone
  in a team.
- **Static Constants vs. Explicit Values:** Some argue constants make modification easier, but explicit values often
  improve readability and help verify business requirements at a glance.
- **Value vs. Formulas:** Should we write the result of `quality - 2`  or use the formula? In some cases, I
  choose the formula since tests serve as documentation. We want to be clear about what is happening.

Using IntelliJ, you can run tests with **coverage analysis** to verify all branches are checked. This becomes much
clearer during refactoring. I trust IntelliJ a lot when rewriting `if` statements and introducing variables, but it's a
good idea to check that the tests still pass against the **original** code whenever possible.

The behavior for *weird* or *invalid* cases is not explicitly mentioned in the requirements. Since we **do not** want to
make any goblins angry, tests have been added to document that behavior **remains unchanged**. Sometimes, users rely on
these odd behaviors, and we should verify that no one is exploiting them before making changes.

### Important Refactoring Steps

- Use `foreach`; `item` is much more readable than `item[i]`.
- Introduce helper function to encapsulate logic.
- Introduce **static constants** for item names. When you click on them Intelij highlights the usages.
- Move changes to `sellIn` to a separate function.
- Split and invert `if` statements using IntelliJ's refactoring tools.
- Group all statements related to a specific item:

  ```java
  switch(item.name){
      case SULFURAS -> ...
      case AGED_BRIE -> ...
      case BACKSTAGE_PASSES -> ...
      default -> ...    
  }
  ```
- Write a **single update action per item name**.
- Return the quality instead of modifying it directly.
- Use only the **necessary arguments** in functions (pass item properties instead of the entire item).
- Use **lambdas/calculators** to transform the `switch` into a list/map/rule engine structure, making testing
  independent of item names.
- Utilize **predicates** to allow for future naming changes.
- Create a **specific rule engine** to manage item rules.
- Prefer **immutability** wherever possible.
- Name of calculator classes
  - As the calculator is not really specific anymore for the item name, they should explain what they are doing, this is very long though
  - (TODO: improve names)

### Steps to Add a Conjured Item After Refactoring

- Add a **Quality Calculator** & test it.
- Add a rule to the **rule engine**.
- Check if the rule is **returned by the engine**.

## Overthinking

#### Anti-Corruption Layer & Deep Copy of the Items

The items can be changed externally—either between `updateQuality` calls or even during execution. To avoid being blamed
for malfunctions, we will create **deep copies** of the items and use our own custom class. Once our calculations are
done, we will map the results back.

This also serves as an **anti-corruption layer** against incorrect or confusing input. For instance, we can implement
our own way of handling `sellIn` updates depending on the provided value.

#### Validator

In the original code, an item with a `quality` of 55 was **not** updated, whereas an item with a `quality` of 49 could
be updated to a maximum of 50.

Since we are **terrified** of the goblin and want to figure out whether this behavior has been abused, we will first
introduce a **validator** that logs any invalid quality values. This allows us to detect if someone has been exploiting
the system.

If nothing suspicious turns up after some time in production, we can transition to throwing errors for invalid
qualities. Let’s hope that doesn’t happen, because... **scary goblin**.

Normally, we’d just throw an error from the start, but given our deep-seated fear of goblins, we’ll proceed cautiously.
Once we confirm that there are no unintended side effects, we can refine our approach and clean up the edge case
handling.

Because the only thing worse than angry goblins… is **angry goblins with a support ticket.**

### Conditionally disable test based on a feature flag

Just wanted to try this for fun.

### Predicate

Predicate for 'Backstage passes' can be changed to something like `ContainsInLowerCase`. (TODO)

### dsl

I just wanted to write a dsl because they are fun to write and easy to read. This way our intern can easily configure
new items.
(dsl is not tested, still in design phase)

-> should consider a more e2e testing framework