package tempest_foundation.Testing;

import tempest_foundation.ClassElements.ClassDetails;

/**
 * The interface representing a test to be executed on a class.
 * <p>
 * This interface defines the essential methods for setting up and executing tests on class details, 
 * including assigning grades for the test results. Any class that implements this interface is expected 
 * to define the behavior of how tests are executed and how grades are calculated based on the class content.
 * </p>
 */
public interface Test {
    void executeTest();

     /**
     * Sets the class details that are to be tested.
     * <p>
     * This method assigns the class details to be tested. It is typically called before 
     * executing the test to ensure the correct class is being evaluated.
     * </p>
     * 
     * @param inTesting the class details to be tested.
     */
    void setClassDetails(ClassDetails inTesting);
    void setGrade(double grade);
}
