<h1 align="center">Bisto Chat using Java and PHP</h1>

### Description
An `Android Chatting Application` coded in `Java Language` using `Android Studio`. Backend is implemented in `PHP Language` i.e, APIs. It works exactly like `Whatsapp` i.e, user accounts get detected by their `Phone Numbers` and `Synced Automatically` with the application. User can then select any contact and start chatting with it.

### Functionalities
- User can `Login/Signup`.
- User can set his `Profile Picture`.
- User can set his `Personal Details`.
- Users can `Send Text Messages` to eachother.
- Users can `Send Image Messages` to eachother.
- Users can `Edit Message` by long pressing it.
- Users can `Delete Message` by long pressing it.
- If a user `Takes Screenshot` of the chat, it gets send in the chat automatically.

### Manual
1) [Download](https://downloadsapachefriends.global.ssl.fastly.net/8.1.6/xampp-windows-x64-8.1.6-0-VS16-installer.exe?from_af=true) and install `XAMPP` from the given link (`C:\xampp` folder should be there after installation).
    ```
    https://downloadsapachefriends.global.ssl.fastly.net/8.1.6/xampp-windows-x64-8.1.6-0-VS16-installer.exe?from_af=true
    ```
2) Paste the folder named as `PHP_Files` present [Here](https://github.com/SameetAsadullah/Hotel-Reservation-Application/blob/main/src/PHP_Files/) in `C:\xampp\htdocs` folder.
3) Open Xampp and `Run Apache and MySQL Services`.
4) `Click on Admin Button` infront of MySQL Service and a new tab will open in the browser.
5) `Create a Database` there with the name `bisto_chat`.
6) `Create the Following Tables` with the `Same Column Names` as displayed in the screenshots below:
    
    <div align="center">
      <img src = "https://github.com/SameetAsadullah/Bisto-Chat-Java-PHP/blob/main/extras/messages-table-ss.png" alt = "" width="800px"/>
    </div>
    <br/>
    <div align="center">
      <img src = "https://github.com/SameetAsadullah/Bisto-Chat-Java-PHP/blob/main/extras/profile-table-ss.png" alt = "" width="800px"/>
    </div>
    
7) Open the provided project in `Android Studio`.
8) Get the `Current IP` of your pc and replace it here in the file named as `Login.java`:
    
    <div align="center">
      <img src = "https://github.com/SameetAsadullah/Bisto-Chat-Java-PHP/blob/main/extras/login-change-ip-ss.png" alt = "" width="800px"/>
    </div>

8) Get the `Current IP` of your pc and replace it here in the file named as `Register.java`:
    
    <div align="center">
      <img src = "https://github.com/SameetAsadullah/Bisto-Chat-Java-PHP/blob/main/extras/register-change-ip-ss.png" alt = "" width="800px"/>
    </div>

10) `Build the Project` and you are good to go :smiley:

### Working Screenshots
<div align="center">
  <img src = "https://github.com/SameetAsadullah/Bisto-Chat-Java-Firebase/blob/master/extras/splash-screen-ss.png" alt = "" width="200px"/>
  <img src = "https://github.com/SameetAsadullah/Bisto-Chat-Java-Firebase/blob/master/extras/login-ss.png" alt = "" width="200px"/>
  <img src = "https://github.com/SameetAsadullah/Bisto-Chat-Java-Firebase/blob/master/extras/signup-ss.png" alt = "" width="200px"/>
  <img src = "https://github.com/SameetAsadullah/Bisto-Chat-Java-Firebase/blob/master/extras/create-profile-ss.png" alt = "" width="200px"/>
</div>
<br/>
<div align="center">
  <img src = "https://github.com/SameetAsadullah/Bisto-Chat-Java-Firebase/blob/master/extras/contacts-ss.png" alt = "" width="200px"/>
  <img src = "https://github.com/SameetAsadullah/Bisto-Chat-Java-Firebase/blob/master/extras/chat-ss.png" alt = "" width="200px"/>
  <img src = "https://github.com/SameetAsadullah/Bisto-Chat-Java-Firebase/blob/master/extras/chats-ss.png" alt = "" width="200px"/>
  <img src = "https://github.com/SameetAsadullah/Bisto-Chat-Java-Firebase/blob/master/extras/chats-ss-1.png" alt = "" width="200px"/>
</div>
<br/>
<div align="center">
  <img src = "https://github.com/SameetAsadullah/Bisto-Chat-Java-Firebase/blob/master/extras/chat-ss-1.png" alt = "" width="200px"/>
  <img src = "https://github.com/SameetAsadullah/Bisto-Chat-Java-Firebase/blob/master/extras/edit-delete-message-ss.png" alt = "" width="200px"/>
  <img src = "https://github.com/SameetAsadullah/Bisto-Chat-Java-Firebase/blob/master/extras/edited-message-ss.png" alt = "" width="200px"/>
</div>
  
### Contributors
- [Sameet Asadullah](https://github.com/SameetAsadullah)
- [Tayyab Ali](https://github.com/DarkDragz)
