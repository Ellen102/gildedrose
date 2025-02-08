# Ellens solution to Gilded Rose

## Changes to setup
- [x] update to java 21
- [x] add assertj
  - Make tests easier to read and it is easier to understand failures
- [ ] update gradle
- [x] mvn test passes
- [x] ./gradlew test passes
- [x] Intelij can run with test coverage

##  understanding, analysing, testing and simple refactoring existing code
goal: make it easy to read

### Tests
- using builders
  - easier to change
  - hide unnecessary details for the test
    - if a value is important for the test, 
    - it should be mentioned explicitly or
    - be very explicit in a create method name
  - java does not have named parameters, makes sure constructors with same type are less easier mixed up
- Use @Nested
  - add structure
  - easy find tests for a specific product 
  - only think about a small part of the code
- Given,when,then structure of tests
  - we can discuss about naming especially with paramaterized tests, but in the end when i want to be clear it always ends up like this
  - yes, I belong to the people that like '_' in test names for clarity
- Originally split up in positive, negative, zero sellin 
  - easier to reason
  - test edge cases for quality is between 0 and 50
  - writing quality-2 in expect makes it easier readable what the behavoir is, some people like it some people dont 
- Should u use constants QUALITY_MAX in tests: easier change test when rules changes BUT it makes tests more difficult to understand
  - I choose not to in this situation
- use intelij to refactor ifs
- 
### Notes
- items are stored without a deep copy, meaning they can be changed by the caller inbetween multiple calls of `updateQuality` method. 
  - Required action: Talk to the goblin to verify he is (ab)using this, if so why. 
  - Can we change the `updateQuality` method to return something?
  - Can we add a getter?
- Notes about existing code
  - Do not hard code item names. 
    - Know for sure they are exactly the same
    - Easier to find occurrences
  - There are currently 4 type of items, currently defined by the *exact* spelling of the name. 
    - AGED_BRIE
    - BACKSTAGE_PASSES -> more specific than in exercise specs, does not work for other backstage passes: discuss with Goblin
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
  - only refactorusing intelij: invert ifs, split if -> makes it easier to understand + easier to understand coverage report :)
  - try to remove sellin update from the middle
  - merge logic per producttype
  - use min,max instead of if(..<max)increase, but we need to keep the same behavoir  with invalid params to not make goblin Mad
  - merge sequantialy updates into 1
  - always place it in positive sellin, negative order
  - return instead of update
      

##  Overthinking...

## step 1: Deep copy of items
The items list can be changed externally by the creator of the list. 
Ideally we would take an argument to the '"update"quality" method, and return a new result
As this is not possible we can introduce an anticorruption layer


### Step 2: The weird behavoir for negative quality and to high qualities



- do not modify values inside 
- use immutability
- handle anticorruption behavoir

