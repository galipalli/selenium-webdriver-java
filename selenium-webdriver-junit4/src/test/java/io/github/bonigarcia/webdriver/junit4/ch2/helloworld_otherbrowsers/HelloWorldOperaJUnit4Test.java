/*
 * (C) Copyright 2021 Boni Garcia (http://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.bonigarcia.webdriver.junit4.ch2.helloworld_otherbrowsers;

import static java.lang.invoke.MethodHandles.lookup;
import static org.apache.commons.lang3.SystemUtils.IS_OS_MAC;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;
import static org.slf4j.LoggerFactory.getLogger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.slf4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

public class HelloWorldOperaJUnit4Test {

    static final Logger log = getLogger(lookup().lookupClass());

    private WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        Path browserPath = getBrowserPath();
        assumeTrue(Files.exists(browserPath));

        // TODO: Use WebDriverManager 5 (not released yet) to get browser path
        // Optional<Path> browserPath = WebDriverManager.operadriver().getBrowserPath();
        // assumeTrue(browserPath.isPresent() && Files.exists(browserPath.get()));

        WebDriverManager.operadriver().setup();
    }

    @Before
    public void setup() {
        driver = new OperaDriver();
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void test() {
        // Exercise
        String sutUrl = "https://bonigarcia.github.io/selenium-webdriver-java/";
        driver.get(sutUrl);
        String title = driver.getTitle();
        log.debug("The title of {} is {}", sutUrl, title);

        // Verify
        assertThat(title).isEqualTo("Hands-on Selenium WebDriver with Java");
    }

    private static Path getBrowserPath() {
        Path browserPath;
        if (IS_OS_WINDOWS) {
            browserPath = Paths.get(System.getenv("LOCALAPPDATA"),
                    "/Programs/Opera/launcher.exe");
        } else if (IS_OS_MAC) {
            browserPath = Paths
                    .get("/Applications/Opera.app/Contents/MacOS/Opera");
        } else {
            browserPath = Paths.get("/usr/bin/opera");
        }
        return browserPath;
    }

}
