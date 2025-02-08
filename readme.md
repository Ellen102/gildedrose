# Ellens solution to Gilded Rose

## Changes to setup
- [x] update to java 21
- [x] add assertj
  - Make tests easier to read and it is easier to understand failures
- [ ] update gradle
- [x] mvn test passes
- [x] ./gradlew test passes
- [x] Intelij can run with test coverage

## Tests
- using builders
  - easier to change
  - hide unnecessary details for the test
    - if a value is important for the test, 
    - it should be mentioned explicitly or
    - be very explicit in a create method name
  - java does not have named parameters, makes sure constructors with same type are less easier mixed up
- Use @Nested
  - add structure
- Given,when,then structure of tests
  - we can discuss about naming especially with paramaterized tests, but in the end when i want to be clear it always ends up like this
  - yes, I belong to the people that like '_' in test names for clarity


## Notes
- items are stored without a deep copy, meaning they can be changed by the caller inbetween multiple calls of `updateQuality` method. 
  - Required action: Talk to the goblin to verify he is (ab)using this, if so why. 
  - Can we change the `updateQuality` method to return something?
  - Can we add a getter?
- Notes about existing code
  - Do not hard code item names. 
    - Know for sure they are exactly the same
    - Easier to find occurrences
  - Update quality also updates sellIn
    - quality depends on sellin and the update behavoir changes when sellin becomes 0
    - sellIn decreases by 1 
      - except for Sulfuras, in the existing code it does not change. 
      - The existing test contains value 0 and -1
      - Based on the requirements I would say it does not exists for this item.
      - Discuss with the Goblin if the value is used, and how/why. 
