# Football World Cup Scoreboard

A simple Live Football World Cup Scoreboard library that keeps all the ongoing matches and their scores.

## Features

- Start a new match with an initial score of 0 - 0
- Update the score of an ongoing match
- Finish a match and remove it from the scoreboard
- Get a summary of all ongoing matches ordered by total score (most recently started match wins ties)

## How to run tests

```
mvn test
```

## Assumptions

- A match is uniquely identified by the combination of home team and away team names
- Starting a match that is already in progress throws an exception
- Starting a match with a team that is already part of another ongoing match throws an exception
- Scores must be the current total for each team (e.g. 2-1, not +1), and cannot be negative
- Finishing or updating a match that doesn't exist throws an exception
- The summary returns a new list and does not modify the internal state

## Notes

- I used an `ArrayList` to store matches since it keeps the insertion order. When two matches have the same total score, the one added later appears first in the summary — no timestamps needed, just the position in the list.
- Validation logic lives in `Scoreboard`, keeping `Match` as a simple data class.
