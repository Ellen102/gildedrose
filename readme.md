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



-------
### Notes

- items are stored without a deep copy, meaning they can be changed by the caller inbetween multiple calls of
  `updateQuality` method.
    - Required action: Talk to the goblin to verify he is (ab)using this, if so why.
    - Can we change the `updateQuality` method to return something?
    - Can we add a getter?
- Notes about existing code
    - Do not hard code item names.
        - Know for sure they are exactly the same
        - Easier to find occurrences
    - There are currently 4 type of items, currently defined by the *exact* spelling of the name.
        - AGED_BRIE
        - BACKSTAGE_PASSES -> more specific than in exercise specs, does not work for other backstage passes: discuss
          with Goblin
        - SULFURAS -> the naming in the code does not match the exercise specs, discuss with Goblin
        - Default item
        - I am using the short names for now, to make it easier to understand the existing code.
    - Update quality also updates sellIn
        - sellIn decreases by 1
            - except for Sulfuras, in the existing code it does not change.
            - The existing test contains value 0 and -1
            - Based on the requirements I would say it does not exists for this item.
            - Discuss with the Goblin if the value is used, and how/why.
        - Sellin is updated in the middle of the method
            - when sell in is originally 0 or less it triggers the second part of the code
- quality depends on sell in and the update behavior changes when sellin becomes 0
  - 
- AGED BRIE: `actually increases in `Quality` the older it gets` -> not very specific
    - it is implements as 0 or negative sell in -> +2 ; positive just +1
- first analyse default flow (unamed products), then sulfuras, then brie, backage passes (in order of complexity)
    - only refactorusing intelij: invert ifs, split if -> makes it easier to understand + easier to understand coverage
      report :)
    - try to remove sellin update from the middle
    - merge logic per producttype
    - use min,max instead of if(..<max)increase, but we need to keep the same behavoir with invalid params to not make
      goblin Mad
    - merge sequantialy updates into 1
    - always place it in positive sellin, negative order
    - return instead of update

## Overthinking...

## step 1: Deep copy of items

The items list can be changed externally by the creator of the list.
Ideally we would take an argument to the '"update"quality" method, and return a new result
As this is not possible we can introduce an anticorruption layer

### Step 2: The weird behavoir for negative quality and to high qualities

- do not modify values inside
- use immutability
- handle anticorruption behavoir

