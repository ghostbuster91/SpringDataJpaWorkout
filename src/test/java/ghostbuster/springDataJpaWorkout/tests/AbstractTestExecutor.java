package ghostbuster.springDataJpaWorkout.tests;


import org.apache.log4j.Logger;

public class AbstractTestExecutor {

    private final Logger logger = Logger.getLogger(this.getClass());

    protected void info(String msg) {
        logger.info(msg);
    }

}
