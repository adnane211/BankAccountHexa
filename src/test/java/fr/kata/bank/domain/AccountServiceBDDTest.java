package fr.kata.bank.domain;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines(value = {"cucumber"})
@SelectClasspathResource("fr/kata/bank/domain")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "fr.kata.bank.domain.port")
public class AccountServiceBDDTest {
}
