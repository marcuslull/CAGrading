# Grading Application
Technical interview homework problem one.  
Completed by Marcus Lull
___

## Problem
### New Code: Grading Application
The Java streams API (java.util.stream.Stream) is an example of functional programming in Java. Using this API, create a stream that generates random final exam grades between 0 and 100 using the below calculation to obtain a lognormal distribution of grades. Be sure to clip any grades under 0 to 0 and over 100 to 100.  

`int grade = (int) (100.0d - Math.exp(random.nextGaussian() * 0.25 + 3.5) + 20);`  

Now, for the first 1,000 random grades from the stream above, calculate and display the following, again using Java streams functionality and functional programming as much as possible:  
1. Basic statistics like the min, max, and average of the 1000 grades
2. The number of students that received each letter grade (A, B, C, D, F) like this: `Letter grade A: 354 students`
   (how concise can you make the code that does this?)
3. A count of how many students got a perfect score of 100.  


**Hints**: read up on lambdas and functional interfaces.

## Process breakdown
1. Understand clearly the problem statement and ask clarifying questions if needed.
2. Prepare the README.md to outline resolution process
3. Explore assumptions and document
4. Technical design
5. Code
6. Test
7. Refactor for optimization
8. Test
9. Documentation
10. Submit for review

## Interpretation and assumptions
* Use the Java streams API where possible
* Generate random grades between 0-100 inclusive using the provided code
  * `int grade = (int) (100.0d - Math.exp(random.nextGaussian() * 0.25 + 3.5) + 20);`
* Any grades < 0 should be 0
* Any grades > 100 should be 100
* Calculate and display data for the first 1000 random grades
* Data
  * Basic statistics - min, max, avg
  * Number of students receiving each grade as: `Letter grade A: 354 students`
  * Count of students receiving 100
* Assumption: Console based application
* Assumption: Adhere to functional programming paradigm
  * avoiding changing state and mutable data
* Assumption: Wording '...first 1,000 grades'
  * I will limit the generation to 1000 grades programmatically
* Assumption: Grading Scale
  * According to a [2009 High School Transcript Study](https://en.wikipedia.org/wiki/Academic_grading_in_the_United_States#cite_note-2) the most common scale is:
  
    | Grade | %      |
    |-------|--------|
    | A     | 90-100 |
    | B     | 80-89  |
    | C     | 70-79  |
    | D     | 60-69  |
    | F     | 0-59   |

## Technical design

## Code notes

## Test notes

## Optimization notes

