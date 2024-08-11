# Spring Boot Server
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
  <li>Build the project with the command:</li>
  <pre><code>./mvnw clean install</code></pre>
  <li>Finally, run the project with the command:</li>
  <pre><code>./mvnw spring-boot:run</code></pre>
</ol>

