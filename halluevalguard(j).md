# Jenkins Practical Experiments

Practical notes for the Jenkins experiments completed on Windows.

## Environment used

* Jenkins installed as a Windows service (not running in Docker).
* Jenkins dashboard: `http://localhost:8080`
* Jenkins version used during the lab: `2.568.3`
* GitHub repository used for the webhook practical:
`https://github.com/anil12345-by/jenkins-webhook-demo`
* ngrok was used to expose the local Jenkins server temporarily so
GitHub could send webhook events.

> Keep Jenkins running as a Windows service while doing these
> practicals. Do not publish Jenkins passwords, API tokens, GitHub
> secrets, or ngrok authentication tokens.

\---

# Experiment 1 --- Create a Freestyle project

## 1\. Open Jenkins

Open this URL in your browser:

``` text
http://localhost:8080
```

Sign in to Jenkins.

## 2\. Create the job

1. Click **New Item**.
2. Enter the item name:

``` text
SE\\\\\\\_Freestyle\\\\\\\_Project
```

3. Select **Freestyle project**.
4. Click **OK**.

## 3\. Add a Windows batch build step

1. Scroll to **Build Steps**.
2. Click **Add build step**.
3. Select **Execute Windows batch command**.
4. Paste:

``` bat
echo =====================================
echo JENKINS FREESTYLE PROJECT
echo Build started successfully
echo =====================================
echo Current directory:
cd
echo Build completed successfully
```

5. Click **Save**.

## 4\. Run the job

1. Click **Build Now**.
2. Open the build under **Build History**.
3. Click **Console Output**.

Expected final result:

``` text
Build completed successfully
Finished: SUCCESS
```

\---

# Experiment 2 --- Create a Scripted Pipeline

## 1\. Create the job

1. From the Jenkins dashboard, click **New Item**.
2. Enter:

``` text
SE\\\\\\\_Scripted\\\\\\\_Pipeline
```

3. Select **Pipeline**.
4. Click **OK**.

## 2\. Add the pipeline script

Scroll to the **Pipeline** section.

Set **Definition** to `Pipeline script`, then paste:

``` groovy
node {
    stage('Preparation') {
        echo 'Preparing the workspace'
    }

    stage('Build') {
        echo 'Building the application'
    }

    stage('Testing') {
        echo 'Running tests'
    }

    stage('Deployment') {
        echo 'Deploying the application'
    }

    stage('Complete') {
        echo 'Pipeline completed successfully'
    }
}
```

Click **Save**.

## 3\. Run the pipeline

1. Click **Build Now**.
2. Open the build in **Build History**.
3. Click **Console Output**.
4. Confirm that all five stages complete and the build ends with:

``` text
Finished: SUCCESS
```

\---

# Experiment 3 --- Configure a GitHub webhook to trigger Jenkins

This practical connects a GitHub repository to a Jenkins Freestyle job.
A push to the repository sends a webhook to Jenkins and triggers a
build.

## 1\. GitHub repository

Repository used:

``` text
https://github.com/anil12345-by/jenkins-webhook-demo
```

The repository contains an `index.html` file. Make a small edit and
commit it when testing the webhook.



<html>

<body>

&#x20;   <h1>Hello from Jenkins Webhook!</h1>

&#x20;   <p>Automatic Build Triggered Successfully</p>

</body>

</html>



## 2\. Create the Jenkins job

1. In Jenkins, click **New Item**.
2. Enter:

``` text
Webhook\\\\\\\_Demo
```

3. Select **Freestyle project**.
4. Click **OK**.

## 3\. Configure Git source control

In the job configuration:

1. Find **Source Code Management**.
2. Select **Git**.
3. Set **Repository URL** to:

``` text
https://github.com/anil12345-by/jenkins-webhook-demo.git
```

4. Set **Branch Specifier** to:

``` text
\\\\\\\*/main
```

5. If the repository is public, credentials can remain unset.

## 4\. Enable the GitHub build trigger

Under **Build Triggers**, select:

``` text
GitHub hook trigger for GITScm polling
```

## 5\. Add the Windows batch build step

Under **Build Steps**, select **Execute Windows batch command** and
paste:

``` bat
echo =====================================
echo GITHUB WEBHOOK DEMONSTRATION
echo GitHub repository connected!
echo Code pulled successfully!
echo Automatic build triggered!
echo =====================================
```

Click **Save**.

## 6\. Make Jenkins reachable from GitHub using ngrok

GitHub cannot send a webhook directly to `localhost`. During the lab,
ngrok provided a temporary public HTTPS address that forwarded traffic
to Jenkins on port 8080.

In a separate Windows terminal, start the ngrok tunnel:

``` powershell
ngrok http 8080
```

Copy the current HTTPS forwarding address shown by ngrok. It will look
similar to:

``` text
https://YOUR-CURRENT-NGROK-ADDRESS
```

Keep the ngrok terminal open while testing. The address can change when
the tunnel is restarted.

If ngrok asks for authentication, configure your own ngrok account token
locally. Never paste the token into GitHub, this README, or a public
repository.

ngrok config add-authtoken 2abc123xyz

ngrok http 8086

## 7\. Add the webhook in GitHub

In the GitHub repository:

1. Open **Settings**.
2. Select **Webhooks**.
3. Click **Add webhook**.
4. Set **Payload URL** to your current ngrok HTTPS address followed by:

``` text
/github-webhook/
```

Example format (replace the hostname with the current ngrok address):

``` text
https://YOUR-CURRENT-NGROK-ADDRESS/github-webhook/
```

5. Set **Content type** to:

``` text
application/json
```

6. Select **Just the push event**.
7. Ensure **Active** is checked.
8. Click **Add webhook**.

For this lab configuration, leave the webhook secret blank unless your
instructor or security setup specifically requires a secret and Jenkins
is configured to validate it.

## 8\. Test the webhook

1. Keep Jenkins and ngrok running.
2. Edit `index.html` in the GitHub repository.
3. Commit and push the change to the `main` branch.
4. Open Jenkins → `Webhook\\\\\\\_Demo`.
5. Check **Build History** for a newly triggered build.
6. Open the build → **Console Output**.

The successful console output observed during the practical included:

``` text
GITHUB WEBHOOK DEMONSTRATION
GitHub repository connected!
Code pulled successfully!
Automatic build triggered!
Finished: SUCCESS
```

A successful webhook test should create a new Jenkins build after the
GitHub push, without manually clicking **Build Now**.

\---

Maven Build with Jenkins – Complete Practical Guide



This document covers the Maven practical using the existing GitHub repository:



\- \*\*Repository:\*\* https://github.com/anil12345-by/maven-java-project

\- \*\*Git branch:\*\* `main`

\- \*\*Jenkins job (Freestyle):\*\* `Maven\_Normal\_Build`

\- \*\*Jenkins job (Pipeline):\*\* `Maven\_Pipeline\_Build`

\- \*\*Jenkins Maven tool name:\*\* `MAVEN HOME`

\- \*\*Jenkins URL (local):\*\* http://localhost:8080



\---



\## 1. Prerequisites



Make sure Jenkins, Git, and Java JDK 17 are available. Configure Maven in Jenkins under \*\*Manage Jenkins → Tools → Maven installations\*\*.



Verify from Windows CMD or PowerShell:



```powershell

java -version

mvn -version

git --version

```



Note: Maven working in your terminal does not necessarily mean it is available to the Jenkins Windows service account. Configure Maven in Jenkins Tools or ensure the service account can access it.



\## 2. Repository details



Use the existing repository; there is no need to create another Java project:



```text

https://github.com/anil12345-by/maven-java-project.git

```



Branch:



```text

main

```



The repository should contain a `pom.xml` at its root.



\---



\## 3. Configure Maven in Jenkins



1\. Open Jenkins: http://localhost:8080

2\. Go to \*\*Manage Jenkins → Tools\*\*.

3\. Find \*\*Maven installations\*\* and click \*\*Add Maven\*\*.

4\. Set the name to `MAVEN HOME`.

5\. Select \*\*Install automatically\*\* and choose an available Maven version, or configure a local Maven installation.

6\. Click \*\*Save\*\*.



The name must match the Pipeline script exactly.



\---



\## 4. Experiment A – Maven build using a Freestyle project



\### 4.1 Create the Jenkins job



1\. Jenkins Dashboard → \*\*New Item\*\*.

2\. Job name: `Maven\_Normal\_Build`.

3\. Select \*\*Freestyle project\*\*.

4\. Click \*\*OK\*\*.



\### 4.2 Configure GitHub source code



Under \*\*Source Code Management\*\*:



1\. Select \*\*Git\*\*.

2\. Repository URL:



&#x20;  ```text

&#x20;  https://github.com/anil12345-by/maven-java-project.git

&#x20;  ```



3\. Branch Specifier:



&#x20;  ```text

&#x20;  \*/main

&#x20;  ```



4\. If the repository is public, leave Credentials as \*\*- none -\*\*. If private, configure Jenkins credentials securely.



\### 4.3 Configure the Maven build step



Under \*\*Build Steps\*\*:



1\. Click \*\*Add build step → Invoke top-level Maven targets\*\*.

2\. Set:

&#x20;  - \*\*Maven Version:\*\* `MAVEN HOME`

&#x20;  - \*\*Goals:\*\* `clean package`

&#x20;  - \*\*POM:\*\* leave blank to use the root `pom.xml`

3\. Click \*\*Save\*\*.



\### 4.4 Run the Freestyle build



1\. Open `Maven\_Normal\_Build`.

2\. Click \*\*Build Now\*\*.

3\. Open the latest build number → \*\*Console Output\*\*.



Expected successful ending:



```text

\[INFO] BUILD SUCCESS

Finished: SUCCESS

```



\---



\## 5. Experiment B – Maven build using a Jenkins Pipeline



\### 5.1 Create the Pipeline job



1\. Jenkins Dashboard → \*\*New Item\*\*.

2\. Job name: `Maven\_Pipeline\_Build`.

3\. Select \*\*Pipeline\*\*.

4\. Click \*\*OK\*\*.



\### 5.2 Configure the Pipeline



In the job configuration, scroll to \*\*Pipeline\*\*:



\- \*\*Definition:\*\* `Pipeline script`

\- Paste this complete script:



```groovy

pipeline {

&#x20;   agent any



&#x20;   tools {

&#x20;       maven 'MAVEN HOME'

&#x20;   }



&#x20;   stages {

&#x20;       stage('Checkout') {

&#x20;           steps {

&#x20;               git branch: 'main',

&#x20;                   url: 'https://github.com/anil12345-by/maven-java-project.git'

&#x20;           }

&#x20;       }



&#x20;       stage('Clean') {

&#x20;           steps {

&#x20;               bat 'mvn clean'

&#x20;           }

&#x20;       }



&#x20;       stage('Compile') {

&#x20;           steps {

&#x20;               bat 'mvn compile'

&#x20;           }

&#x20;       }



&#x20;       stage('Test') {

&#x20;           steps {

&#x20;               bat 'mvn test'

&#x20;           }

&#x20;       }



&#x20;       stage('Package') {

&#x20;           steps {

&#x20;               bat 'mvn package'

&#x20;           }

&#x20;       }



&#x20;       stage('Complete') {

&#x20;           steps {

&#x20;               echo 'Maven build completed successfully!'

&#x20;           }

&#x20;       }

&#x20;   }



&#x20;   post {

&#x20;       success {

&#x20;           echo 'BUILD SUCCESS'

&#x20;       }



&#x20;       failure {

&#x20;           echo 'BUILD FAILED - Check Console Output'

&#x20;       }

&#x20;   }

}

```



Click \*\*Save\*\*.



Important:

\- `maven 'MAVEN HOME'` must match the Maven installation name in Jenkins.

\- `bat` is used because Jenkins is running on Windows.



\### 5.3 Run the Pipeline



1\. Open `Maven\_Pipeline\_Build`.

2\. Click \*\*Build Now\*\*.

3\. Open the latest build.

4\. View \*\*Console Output\*\* or \*\*Pipeline Stage View\*\*.



Expected successful ending:



```text

\[INFO] BUILD SUCCESS

Finished: SUCCESS

```



\### 5.4 Understand the Pipeline stages



| Stage | Purpose |

|---|---|

| Checkout | Checks out the `main` branch from GitHub |

| Clean | Removes previous Maven build output |

| Compile | Compiles Java source code |

| Test | Runs project tests |

| Package | Creates the configured JAR/WAR package |

| Complete | Prints a completion message |



Note: Running Maven commands in separate stages can repeat lifecycle work. This is intentional here to demonstrate the phases separately.



\---



\## 6. Maven commands reference



Run these commands from the project directory (the directory containing `pom.xml`):



```powershell

mvn clean

mvn validate

mvn compile

mvn test

mvn package

mvn verify

mvn install

mvn clean package

```



| Command | Purpose |

|---|---|

| `mvn clean` | Removes generated output from the previous build, usually `target/` |

| `mvn validate` | Checks project configuration |

| `mvn compile` | Compiles the main source code |

| `mvn test` | Runs project tests |

| `mvn package` | Packages compiled code, commonly as a JAR or WAR |

| `mvn verify` | Runs checks during the verify lifecycle phase |

| `mvn install` | Installs the built artifact in the local Maven repository |

| `mvn clean package` | Cleans and then builds/packages the project |



\---



\## 7. Git commands reference



Clone the repository:



```powershell

git clone https://github.com/anil12345-by/maven-java-project.git

```



Enter the project folder:



```powershell

cd maven-java-project

```



Check the working tree:



```powershell

git status

```



Pull latest changes from `main`:



```powershell

git pull origin main

```



Stage changes:



```powershell

git add .

```



Commit changes:



```powershell

git commit -m "Update Maven project"

```



Push changes:



```powershell

git push origin main

```



Only run `git add`, `git commit`, and `git push` when you have changes you intend to publish. GitHub authentication may require configured credentials or browser-based sign-in.





































# Quick reference

Practical      Jenkins item             Type

\---

Experiment 1   `SE\\\\\\\_Freestyle\\\\\\\_Project`   Freestyle project
Experiment 2   `SE\\\\\\\_Scripted\\\\\\\_Pipeline`   Pipeline
Experiment 3   `Webhook\\\\\\\_Demo`           Freestyle + GitHub webhook

## Important URLs

``` text
Jenkins dashboard:
http://localhost:8080

GitHub repository:
https://github.com/anil12345-by/jenkins-webhook-demo
```

## Troubleshooting checklist

### Jenkins page does not open

* Check that the Jenkins Windows service is running.
* Try `http://localhost:8080` again.
* Port 8080 was used by Jenkins during this practical; avoid assigning
Docker containers to host port 8080.

### Freestyle or Pipeline build fails

* Open the failed build → **Console Output**.
* Read the first error message.
* Check that the correct build step or Pipeline script was saved.

### GitHub webhook does not trigger a build

* Confirm ngrok is still running and forwarding to `localhost:8080`.
* Confirm the GitHub webhook URL uses the current ngrok HTTPS address
and ends in `/github-webhook/`.
* Confirm **GitHub hook trigger for GITScm polling** is enabled on
`Webhook\\\\\\\_Demo`.
* Confirm the GitHub push was made to the `main` branch.
* Check GitHub repository **Settings → Webhooks → Recent Deliveries**
for the delivery response.
* Check Jenkins build history and console output.

### Security and cleanup

* Stop the ngrok tunnel with `Ctrl+C` when the webhook practical is
finished.
* Do not commit Jenkins credentials, API tokens, ngrok tokens, private
keys, or secrets to GitHub.
* Keep Jenkins access protected; do not expose an unauthenticated
Jenkins server publicly.

