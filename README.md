# Automated Website Health Checker

Automated Website Health Checker is a Java-based project aimed at automating the testing and health evaluation of websites. Using Selenium WebDriver and TestNG, this project enables automated checks for various aspects of a website, ensuring its functionality, performance, and reliability.

## Features

- **Page Load Time Evaluation:** Measures and reports the time taken for web pages to load, helping to identify performance bottlenecks.
- **Broken Links Detection:** Automatically scans the website for broken links (HTTP response code >= 400), ensuring all links are functional.
- **Missing Images Detection:** Checks for missing images (HTTP response code 404), ensuring all images are properly displayed.
- **Customizable Configuration:** Uses a `data.properties` file to specify the URL of the website under test, providing flexibility in testing different websites.
- **Reporting:** Generates detailed reports in HTML format, providing insights into test results including passed, failed, and skipped tests.

## Technologies Used

- **Java:** Core programming language for developing the automation scripts.
- **Selenium WebDriver:** Automation tool used to interact with web browsers.
- **TestNG:** Testing framework for organizing and executing test cases.
- **Chrome WebDriver:** Browser automation driver for Chrome browser.


## Getting Started

To run the Automated Website Health Checker on your local machine, follow these steps:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/M-Ehtesham-Ul-Hassan-Malik/Automated-Website-Health-Checker.git
   ```

2. **Setup WebDriver:**
    - Download the appropriate `chromedriver.exe` for your Chrome browser version and place it in the `drivers/` directory.

3. **Configure URL:**
    - Open `data.properties` and update the `url` property with the URL of the website you want to test.

4. **Run Tests:**
    - Open `Runner.xml` and configure any test parameters if needed.
    - Execute the tests using TestNG. You can run it via command line or using your preferred IDE.

5. **View Reports:**
    - After execution, view the HTML reports located in the `test-output/` directory for detailed test results.

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your improvements or bug fixes.

## License

This project is licensed under the MIT License. See the [LICENSE] file for more details.

