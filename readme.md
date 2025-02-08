# Ellens solution to Gilded Rose

## Changes to setup
- [x] update to java 21
- [x] add assertj
  - Make tests easier to read and it is easier to understand failures
- [ ] update gradle
- [x] mvn test passes
- [x] ./gradlew test passes

## Tests
- using builders
  - easier to change
  - hide unnecessary details for the test
  - java does not have named parameters, makes sure constructors with same type are less easier mixed up

## Notes
- items are stored without a deep copy, meaning they can be changed by the caller inbetween multiple calls of `updateQuality` method. 
  - Required action: Talk to the goblin to verify he is (ab)using this, if so why. 
  - Can we change the `updateQuality` method to return something?
  - Can we add a getter?
- Do not hard code item names. 
- Do