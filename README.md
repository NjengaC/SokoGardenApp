## Android App Project Overview
In this repository, we will create an Android application that interacts with an external API hosted at:
👉 https://github.com/modcomlearning/BackendAPI

This app will serve as an e-commerce client that communicates with the backend to handle users, products and transactions.

📱 App Features

The Android app will include the following main pages:
<br>
1. Main Page – Landing screen with general app info or product listing
<br>
2. Signup Page – Allows new users to register
<br>
3. Signin Page – Allows existing users to log in
<br>
4. Buy Products Page – Displays available products and allows users to select
<br>
5. Make Payment Page – Final checkout and payment interface
6. <br>


### 🛠️ Step 1: Create a New Android Project
To get started:

Open Android Studio <br>

Click on "New Project" <br>

Choose an appropriate template (use., Empty Views Activity) <br>

<b>Name the project:</b>
 <i>YourName_Project </i>

(Replace YourName with your actual name)
<br>
Choose the programming language -  Kotlin.
<br>
Set the minimum SDK (e.g., API 21 or higher)
<br>
Click <b>Finish</b> to create the project

In Your New Created Project, The MainActivity is Created.
Next, Go to <b>res - layout - activity_main.xml</b> , In this Layout Create a Main Page of our application.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="DailyYoghurts"
        android:background="@drawable/shape"
        android:textColor="@color/white"
        android:textSize="40sp"
        android:textAlignment="center"
        android:layout_marginBottom="10dp"
        android:textStyle="bold"/>
    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Sign Up"
        android:backgroundTint="#448AFF"
        android:id="@+id/signup"/>
    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Sign In"
        android:backgroundTint="#40C4FF"
        android:id="@+id/signin"/>
</LinearLayout>
```

Above LinearLayout holds a TextView and Two buttons namely Sign Up and Sign in.
Output

![img.png](img.png)

<b>Add App Dependencies </b> <br>
During the development of this app, we will need to add below dependencies to be used in api access and image loading
<br>
In your App gradle files, open build.gradle - (Module App) and add below lines under dependencies.

```kotlin
   dependencies {
       
       ...
 
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
    implementation("com.loopj.android:android-async-http:1.4.11")
}
```
Click on Sync Project.

Next Go to https://justpaste.it/iyuh1 , Copy the ApiHelper Class code ans Paste in Your Main App Package.
The ApiHelper class will be used in APIs Access.

Run App.

### 🛠️ Step 2: Creating a Signup Activity
In this step we create a Signup Activity, This Acitivity will have a registration form with for fields namely username, email, phone and password.
Right Click on App Main Package - <b>New- Activity - Select Empty Views Activity </b> template<br> Give this Activity the name "Signup" Click Finish.
<br>

In the New Created Activity, Open res-layout-activity_signup.xml and create below layout

```xml
<?xml version="1.0" encoding="utf-8"?>
    <LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="20dp"
        android:orientation="vertical">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Create Account"
            android:textSize="24sp"
            android:textStyle="bold"
            android:gravity="center"
            android:layout_marginBottom="24dp" />

        <EditText
            android:id="@+id/username"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Enter UserName"
            android:inputType="textPersonName"
            android:padding="12dp"
            android:layout_marginBottom="16dp" />

        <EditText
            android:id="@+id/email"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Email Address"
            android:inputType="textEmailAddress"
            android:padding="12dp"
            android:layout_marginBottom="16dp" />
        <EditText
            android:id="@+id/phone"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Phone (Optional)"
            android:inputType="phone"
            android:padding="12dp"
            android:layout_marginBottom="16dp" />


        <EditText
            android:id="@+id/password"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Password"
            android:inputType="textPassword"
            android:padding="12dp"
            android:layout_marginBottom="24dp" />

        <Button
            android:id="@+id/signup"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="SIGN UP"
            android:backgroundTint="#000000"
            android:textColor="#FFFFFF"
            android:padding="12dp" />
    </LinearLayout>

```

In above XML Layout we have 4 Edittexts, note that each EditText has a unique ID as well as the Signup Button.

![img_1.png](img_1.png)

Next, Create the Layout logic to capture user enetered data and send to our API.
Find Sign up API under https://github.com/modcomlearning/BackendAPI  Step 4

Go to kotlin + java, Open Signup.kt and update the Activity as shown in below Code.

```kotlin
package com.modcom.yoghurts
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.loopj.android.http.RequestParams

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Find Views By ID
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val email = findViewById<EditText>(R.id.email)
        val phone = findViewById<EditText>(R.id.phone)
        val signup = findViewById<Button>(R.id.signup)
        //Set Button Helper
        signup.setOnClickListener {
            //Set the API Endpoint URL
            val api = "https://modcom2.pythonanywhere.com/api/signup"
            
         //Put text/values from EditTexts to RequestParams - holds data
            val data = RequestParams()
            data.put("username", username.text.toString())
            data.put("password", password.text.toString())
            data.put("email", email.text.toString())
            data.put("phone", phone.text.toString())
            //Access ApiHelper Class
            val helper = ApiHelper(applicationContext)
            //Post the data to our API
            helper.post(api, data)
        }
    }
}
```

<b>Above Code;</b>
1. Finds the 4 EditTexts and 1 Button

   val username = findViewById<EditText>(R.id.username)
   val password = findViewById<EditText>(R.id.password)
   val email = findViewById<EditText>(R.id.email)
   val phone = findViewById<EditText>(R.id.phone)
   val signup = findViewById<Button>(R.id.signup)

2. Sets Listener to Button (Listens when Button is Clicked)

   signup.setOnClickListener {
        ...
3. Specifies the API Endpoint

   val api = "https://modcom2.pythonanywhere.com/api/signup"

4. Gets all Texts/Values entered by user in EditTexts, Put the them in RequestParams

   val data = RequestParams()
   data.put("username", username.text.toString())
   data.put("password", password.text.toString())
   data.put("email", email.text.toString())
   data.put("phone", phone.text.toString())

5. Access the APiHelper and Sends the data.

   val helper = ApiHelper(applicationContext)
   //Post the data to our API
   helper.post(api, data)


Finally, we need to Link the Signup Button in MainActivity to Link to Signup Activity, To do this Open MainActivity and add below code.

```kotlin
  val signup = findViewById<Button>(R.id.signup)
  signup.setOnClickListener {
   val intent = Intent(applicationContext, Signup::class.java)
   startActivity(intent)
  }
```
Run App
Click on Sign Up Button, It Opens a Sign up Form, Fill in Details and Submit
The Data is sent to out API.

<img src = "img_2.png" width = "300"/>
<img src = "img_1.png" width = "300"/>










