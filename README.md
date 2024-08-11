<h2>Running the Project</h2>

<p>To run this project, follow these steps:</p>

<ol>
  <li>First, clone the project.</li>
  <li>Create a <code>.env</code> file inside the <code>src/main/resources/</code> directory and declare the values for the variables as follows:</li>
  <ul>
    <li><code>MYSQL_DATABASE=&lt;path to the database&gt;</code></li>
    <li><code>MYSQL_USER=&lt;database username&gt;</code></li>
    <li><code>MYSQL_PASSWORD=&lt;password&gt;</code></li>
  </ul>
  <li>Refresh the project.</li>
  <li>Ensure your environment is configured with the following versions:</li>
  <ul>
    <li><strong>Java Version:</strong> 17</li>
    <li><strong>Spring Boot Version:</strong> 3.3.2</li>
    <li><strong>PostgreSQL Version:</strong> 16</li>
  </ul>
  <li>Build the project with the command:</li>
  <pre><code>./mvnw clean install</code></pre>
  <li>Finally, run the project with the command:</li>
  <pre><code>./mvnw spring-boot:run</code></pre>
</ol>

<p>If you encounter any issues, ensure that your environment is correctly set up for Java and Spring Boot development:</p>

<ul>
  <li>Make sure you have the appropriate Java version installed.</li>
  <li>Ensure that PostgreSQL is correctly installed and configured.</li>
  <li>Gradle or Maven is properly set up and up to date.</li>
</ul>
