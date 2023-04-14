package com.potpiefry.data

data class Dish(val id: Int, val name: String, val description: String)

val dishes = listOf(
	Dish(1, "Pierogi ruskie", "Klasyczne polskie pierogi z nadzieniem ziemniaczano-serowym"),
	Dish(
		2,
		"Spaghetti Bolognese",
		"Klasyczne danie kuchni włoskiej z sosem mięsnym na bazie mielonego mięsa"
	),
	Dish(
		3,
		"Kotlet schabowy",
		"Tradycyjne danie polskie z kotletem schabowym panierowanym w bułce tartej"
	),
	Dish(4, "Sushi", "Japońska potrawa z surowym rybą i warzywami podanymi na kwaszonym ryżu"),
	Dish(5, "Pizza Margherita", "Klasyczna włoska pizza z sosem pomidorowym, mozzarellą i bazylią"),
	Dish(
		6,
		"Kebab",
		"Potrawa kuchni tureckiej z kawałkami mięsa, warzywami i sosem, podawana w bułce"
	),
	Dish(7, "Chili con carne", "Meksykańska potrawa z mielonym mięsem, fasolą i przyprawami"),
	Dish(
		8,
		"Tacos",
		"Meksykańska potrawa z mięsem, warzywami i sosem, podawana w cieście tortilli"
	),
	Dish(
		9,
		"Lasagne",
		"Klasyczne danie kuchni włoskiej z warstwami makaronu, mięsa i sosu beszamelowego"
	),
	Dish(
		10,
		"Sałatka Cezar",
		"Klasyczna sałatka z sałatą rzymską, kurczakiem, jajkiem, parmezanem i sosem cezar"
	)
)

