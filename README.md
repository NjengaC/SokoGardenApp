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

<img src="img.png" width="300"/>

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

### 🛠️ Step 2: Creating a Signin Activity
In this Step, we create a Signin Functionality for our App. This will be used by users to Login to the application.
Right Click on App Main Package - <b>New - Activity - Select Empty Views Activity </b> template<br> Give this Activity the name "Signin" Click Finish.
<br>

In the New Created Activity, Open res-layout-activity_signin.xml and create below layout

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="20dp"
    tools:context=".Signin">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Sign In"
            android:textSize="24sp"
            android:textStyle="bold"
            android:gravity="center"
            android:layout_marginBottom="24dp" />

        <EditText
            android:id="@+id/email"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Email Address"
            android:inputType="textEmailAddress"
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
            android:id="@+id/signin"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="SIGN IN"
            android:backgroundTint="#000000"
            android:textColor="#FFFFFF"
            android:padding="12dp" />

    </LinearLayout>

```
<b>In Above Layout; </b> <br>
1. There are Two EditTexts withs ids @+id/email and @+id/password. <br>
2. There is one button with id @+id/signin  <br>

Next, Go to Kotlin + Java Folder, Open Signin Kotlin Class and update your code to look like below.

```kotlin
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.loopj.android.http.RequestParams
import com.modcom.yoghurts.ApiHelper
import com.modcom.yoghurts.R

class Signin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Find Views By ID
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val signin = findViewById<Button>(R.id.signin)

        //Set Button Listener
        signin.setOnClickListener {
            //Set API - Endpoint
            val api = "https://modcom2.pythonanywhere.com/api/signin"
            //Add texts from EditTexts to RequestParams, email, password
            val data = RequestParams()
            data.put("email", email.text.toString().trim())
            data.put("password", password.text.toString().trim())
            //Access helper and Post
            val helper = ApiHelper(applicationContext)
            helper.post_login(api, data)
        }
    }
}
```
<b>Above Code;</b>
1. Finds the 2 EditTexts and 1 Button

   val email = findViewById<EditText>(R.id.email)
   val password = findViewById<EditText>(R.id.password)
   val signin = findViewById<Button>(R.id.signin)

2. Sets Listener to Button (Listens when Button is Clicked)

   signin.setOnClickListener {
   ...
3. Specifies the API Endpoint

   val api = "https://modcom2.pythonanywhere.com/api/signin"

4. Gets all Texts/Values entered by user in EditTexts, Put the them in RequestParams

   val data = RequestParams()
   data.put("email", email.text.toString().trim()) 
   data.put("password", password.text.toString().trim())

5. Access the ApiHelper and Sends the data to API

   val helper = ApiHelper(applicationContext)
   //Post the data to our API
   helper.post_login(api, data)

<br>
   
Finally, we need to Link the Signin Button from MainActivity to Link/Intent to Signin Activity, To do this Open MainActivity and add below code inside onCreate Function.

```kotlin
  val signin = findViewById<Button>(R.id.signin)
  signin.setOnClickListener {
   val intent = Intent(applicationContext, Signin::class.java)
   startActivity(intent)
  }
```


Run App
Click on Sign In Button, It Opens a Sign up Form, Fill in Details and Submit
The Data is sent to out API.

<p float="left">
  <img src="img_2.png" width="300"/>
  <img src="img_3.png" width="300"/>
</p>


### 🛠️ Step 3: Creating a Signup Activity
In this step we create a Signup Activity, This Activity will have a registration form with for fields namely username, email, phone and password.
Right Click on App Main Package - <b>New- Activity - Select Empty Views Activity </b> template<br> Give this Activity the name "Signup" Click Finish.
<br>

In the New Created Activity, Open <b>res-layout-activity_signup.xml</b> and create below layout

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

In above XML Layout we have 4 EditTexts, note that each EditText has a unique ID as well as the Signup Button.

![img_1.png](img_1.png)

Next, Create the Layout logic to capture user enetered data and send to our API.
Find Sign up API under https://github.com/modcomlearning/BackendAPI  Step 4

Go to kotlin + java, Open Signup.kt and update the Activity as shown in below Code.

```kotlin

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


Finally, we need to Link the Signup Button in MainActivity to Link to Signup Activity, To do this Open MainActivity and add below code inside the onCreate function.

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

<p float="left">
  <img src="img_2.png" width="300"/>
  <img src="img_1.png" width="300"/>
</p>


### 🛠️ Step 4: Creating a Display Products Functionality.
In this Step, we create the Get Products Functionality, Here, all the products will be retrieved and displayed in MainActivity, Just below the Signin and Signup Buttons, See below screenshot
<br>
<p float="left">
  <img src="img_2.png" width="300"/>
  <img src="img_4.png" width="300"/>
</p>
Products will be displayed in <b>MainActivity</b> <br>
To Start with, Create an XML Layout of how one Product will be displayed. To Create a New XML File,  Right Click on res Folder - <b>New -  XML - Layout XML File </b> <br>

Name this XML <b>single_item</b> and Create below Layout.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="16dp">

   <!-- Product Name -->
   <TextView
           android:id="@+id/product_name"
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:text="Product Name"
           android:textSize="18sp"
           android:textStyle="bold"
           android:textColor="#000000" />

   <!-- Product Photo -->
   <ImageView
           android:id="@+id/product_photo"
           android:layout_width="match_parent"
           android:layout_height="150dp"
           android:layout_marginTop="8dp"
           android:scaleType="centerInside"
           android:src="@mipmap/ic_launcher" />

   <!-- Product Cost -->
   <TextView
           android:id="@+id/product_cost"
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:text="KES 100"
           android:textStyle="bold"
           android:textSize="16sp"
           android:textColor="#FF5722"
           android:layout_marginTop="4dp" />

   <!-- Product Description -->
   <TextView
           android:id="@+id/product_description"
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:text="This is a great product with amazing features."
           android:textSize="14sp"
           android:textColor="#555555"
           android:layout_marginTop="4dp" />


   <Button
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:text="PURCHASE NOW"
           android:layout_marginBottom="20dp"
           android:layout_marginTop="10dp"
           android:id="@+id/purchase"
           android:backgroundTint="#000000"/>
</LinearLayout>
```

In Your Android Studio Layout View, It should look like this .

<img src="img_5.png" width="1000"/>

🤖 <b>What is a RecyclerView in Android? </b> <br>

 1. RecyclerView is a special view used to display a list of items (like names, products, messages) in a scrollable way.
 <br>
 2. It’s a better version of List — faster and more flexible.
<br>
 3. Example use: showing a list of products, contacts, or messages in your app.
<br>
<i>Example of a Recycler View Showing Planets </i> <br>

<p float="left">
  <img src="img_6.png" width="300"/>
  <img src="img_7.png" width="300"/>
</p>
<br>
🧩 <b>Why Do We Need an Adapter?</b> <br>

 1. The Adapter acts like a bridge between your data (like a list of products) and the RecyclerView UI.

 2. It takes each item in your list and tells RecyclerView how to show it on the screen.

 3. Without the adapter, RecyclerView won’t know what to display or how to display it.

Therefore, we need a RecyclerAdapter, Go to https://justpaste.it/h3e6v  and Copy the RecyclerAdapter Class, Paste it your App Main Package<br>

Then Go to activity_main.xml and add below 2 Views.

```xml
    <ProgressBar
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/progressbar"/>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        tools:listitem="@layout/single_item"/>
```

In Above Code;
1.  <ProgressBar> - Shows progress as we retrieve products.
2.  <RecyclerView> - Creates a List of Products, Links to single_item XML layout using <b>tools:listitem.</b>
                   - single_item - Helps recycler view kow how to display products.


Your Complete activity_main.xml 
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


    <ProgressBar
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/progressbar"/>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        tools:listitem="@layout/single_item"/>
   
</LinearLayout>
```

Next, Go to kotlin + java, Open MainActivity and write below Code.

```kotlin
        // Fetch recylerview and progressbar
        val progressBar = findViewById<ProgressBar>(R.id.progressbar)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        //  Define your url
        val url = "https://modcom2.pythonanywhere.com/api/get_product_details"

        //  Get the object of class APiHelper
        val helper = ApiHelper(applicationContext)

        //  We have a function called loadProducts, its inside ApiHelper
        //  Pass the 3 arguments which are url(api endpoit), recyclerview and progressbar
        helper.loadProducts(url, recyclerView, progressBar )
```










