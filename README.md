# BuycraftAPI

BuycraftAPI brings [Buycraft](https://buycraft.net) and Java together. This is made for developers that want to use Buycraft on their Minecraft servers just to show last donators on signs or create cool statues without all the Buycraft plugin.

## Installation
BuycraftAPI is distributed as a [maven](http://maven.apache.org/) project. To compile it and install it in your local Maven repository (.m2) use:

```
git clone https://github.com/Hugmanrique/BuycraftAPI
cd BuycraftAPI
mvn clean install
```

## Usage
To start getting some data you need to create an instance of `BuycraftAPI` where you have to provide your private API Server Key.

Then, you can change if you want the queries to be made via HTTPS or HTTP with `BuycraftAPI.setSecure(boolean)`. By default the API will use HTTPS.

Now you can use all the methods inside this class. If you don't know how something works, read the Javadocs, all the methods are well documented

## Examples
Get 10 latest people that bought a package and how much they payed:

```java
try {
    BuycraftApi api = new BuycraftApi("YOUR_API_KEY");
    Set<Payment> latest = api.getLatestPayments(10);
    
    for (Payment payment : latest){
        System.out.println("- " + payment.getPlayerName() + " payed " + payment.getAmount() + payment.getCurrencySymbol());
    }
    
} catch (BuycraftException e) {
    e.printStackTrace();
}
```

Get all the Packages and display them:
```java
try {
    BuycraftApi api = new BuycraftApi("YOUR_API_KEY");
    Set<Category> categories = api.getListing();
    
    for (Category category : categories){
        System.out.println("- " + category.getName() + " has " + category.getPackages().size() + " packages and " + category.getSubCategories().size() + " subcategories");
    }
} catch (BuycraftException e){
    e.printStackTrace();
}
```

## License
This project is licensed under the GNU General Public License. You can read all the license terms [here](LICENSE)

## Donate
If you think this project is useful for you or your server, please, consider donating [here](https://paypal.me/Hugmanrique)