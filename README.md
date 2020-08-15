

<br/>
<p align="center">
    <a href="https://resqlity.com/" target="_blank">
        <img width="80%" src="https://resqlity.com/resqlity/logo.png" alt="Resqlity logo">
    </a>
</p>



## Resqlity

[**Resqlity**](https://resqlity.com) is a software organization that aims to provide free database solutions for business owners and software developers, and now only offers database service to you.
 It will offer you services such as search, cache usage, instant-notification, authorization, and cloud storage after the necessary tests and optimization processes have been performed recently.
In this process, every feedback from **Android and iOS Developers** will further accelerate the rapid development of Resqlity.


## Introduction

[![GitHub license](https://img.shields.io/github/license/Naereen/StrapDown.js.svg)](https://github.com/Naereen/StrapDown.js/blob/master/LICENSE) [![GitHub release](https://img.shields.io/github/release/Naereen/StrapDown.js.svg)](https://GitHub.com/Resqlity/Resqlity-Android/releases/) [![On Bintray](https://img.shields.io/badge/On-Bintray-<COLOR>.svg)](https://bintray.com/yalcinberkay/Resqlity-Android/)


[Resqlity-Android](https://resqlity.com/) is **android connector library** for Resqlity that provides relational database, real-time database, cache storage, in-app search, push-notification for software.

## Features



| Feature Name  | Resqlity | Resqlity-Android Library | 
|--|--|--|
| Querying | <center>:heavy_check_mark:</center> |<center>:heavy_check_mark:</center>|
| Caching| <center>:heavy_check_mark:</center> |<center>:heavy_check_mark:</center>|
| Push Notification| <center>:heavy_check_mark:</center> |<center>:heavy_check_mark:</center>|
| Real-Time Database| <center>:heavy_check_mark:</center> |<center>:x:</center>|
| In-App Searching| <center>:heavy_check_mark:</center> |<center>:x:</center>|
| Cloud-Storage| <center>:x:</center> |<center>:x:</center>|
| Ruleset Based Operations| <center>:x:</center> |<center>:x:</center>|
| In-App Authorization| <center>:x:</center> |<center>:x:</center>|

## 🚀&nbsp; Installation and Documentation

The [Resqlity-Android](https://github.com/resqlity/resqlity-android) repository provides a **Resqlity Android Library** to quick-start your development. Visit the official [Resqlity-Android Documentation](https://resqlity.com/documentation/android) to find out how to use the Resqlity for starting your own awesome project.

## Examples

Just Get New Instance Of ResqlityContext

```java
ResqlityContext context = new ResqlityContext("YOURAPIKEY",Context,PushNotificationIconResourceCode);
```

**Get Entities**

```java
ResqlityResponse<List<Customers>> customers	= 
	context.Select(Customers.class)
	.Select("firstName")
	.Select("lastName")
	.Select("phone","email","city","zipCode")
	.Where("firstName","berkay",Comparator.Equal)
	.Or("firstName",null,Comparator.IsNull)
	.Where("birthDate","1990-08-01",Comparator.GreatherThan)
	.Query() 		// Get Base Query
	.OrderBy("firstName",true)
	.ThenBy("lastName",false)
	.Query()		// Get Base Query
	.PageBy(1,10) 	// Page 1, Page Size 10
	.Execute(true,false); // Use Cache : true , Flush Cache : false
```
**Insert & Bulk Insert**
```java
List<Customers> customers=new ArrayList<>();  
Customers customer1=new Customers("Jone", "Doe", "90xxxxxxxx", "jone.doe@gmail.com", "Wolf Street", "NY", "NY", "x");  
Customers customer2=new Customers("Jone", "Doe", "90xxxxxxxx", "jone.doe@gmail.com", "Wolf Street", "NY", "NY", "x");  
Customers customer3=new Customers("Jone", "Doe", "90xxxxxxxx", "jone.doe@gmail.com", "Wolf Street", "NY", "NY", "x");  
Customers customer4=new Customers("Jone", "Doe", "90xxxxxxxx", "jone.doe@gmail.com", "Wolf Street", "NY", "NY", "x");

customers.add(customer3);
customers.add(customer4);
	
ResqlitySimpleResponse insertResponse = context.Insert(Customers.class)
	.Insert(customer1)
	.Insert(customer2)
	.Insert(customers)
	.Execute();
```
**Update Entities**
```java
ResqlityResponse<Integer> updateResponse = context.Update(Customers.class)
	.Update("firstName","John")
	.Update("lastName","Doe")
	.Where("firstName","Berkay",Comparator.Equal)
	.And("lastName",null,Comparator.IsNotNull)
	.Query()
	.Execute(true); // Use Transaction : true
```
**Delete Entities**

```java
ResqlityResponse<Integer> deleteResponse = context.Delete(Customers.class)
	.Where("firstName","John",Comparator.NotEqual)
	.Or("firstName","Berkay",Comparator.Equal)
	.And("lastName",null,Comparator.IsNotNull)
	.Query()
	.Execute(true); // Use Transaction : true
```



## ❤️&nbsp; Community and Contributions

The Resqlity-Android is a **community-driven open source project**. We are committed to a fully transparent development process and **highly appreciate any contributions**. Whether you are helping us fixing bugs, proposing new feature, improving our documentation or spreading the word - **we would love to have you as part of the Resqlity community**.



## 🤝&nbsp; Found a bug? Missing a specific feature?

Feel free to **file a new issue** with a respective title and description on the the [resqlity/resqlity-android](https://github.com/Resqlity/Resqlity-Android/issues) repository. If you already found a solution to your problem, **we would love to review your pull request**!


## ✅&nbsp; Requirements

**Resqlity-Android requires Android SDK 26**


## 📘&nbsp; License
The Resqlity-Android is released under the under terms of the [MIT License](LICENSE).
