## River Flow Calculation

### System Requirement
- Java 11+
- Gradle

### Project Feature
- `CrossSectionPoint` has field `distance`, `depth`, `velocity` to calculate 
  the volume of water. The field `id` is to identify and update the velocity 
  at the point.  
- `CrossSection` is the container for `CrossSectionPoint` to keep the sorted list 
  of `CrossSectionPoint` from distance=0(the closest bank) to the farthest bank.
- if there is no velocity censor at the bank, the velocity will get from the 
  closest one.
- The volume of water is the summary of volume at each point by calculate 
  trapezoid area before and after the point multiplied by velocity of the water flow 
  at the point and time.
- All classes are thread-safe.
- Using Spock framework to implement unit tests

### Running
- Running test by `./gradlew test`
- Test report is at `/revier-be/build/reposts/tests/test/index.html`
