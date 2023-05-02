package com.potpiefry.data

enum class TabType(val title: String) {
	Start("Widok główny"),
	Local("Kuchnia polska"),
	Abroad("Kuchnia świata")
}

data class Dish(
	val id: Int,
	val name: String,
	val description: String,
	val type: TabType,
	val img: String
)

val dishes = listOf(
	Dish(
		1,
		"Pierogi ruskie",
		"Klasyczne polskie pierogi z nadzieniem ziemniaczano-serowym",
		TabType.Local,
		"https://cdn.aniagotuje.com/pictures/articles/2020/01/2063434-v-1080x1080.jpg"
	),
	Dish(
		2,
		"Spaghetti Bolognese",
		"Klasyczne danie kuchni włoskiej z sosem mięsnym na bazie mielonego mięsa",
		TabType.Abroad,
		"https://kuchnia-domowa.pl/images/content/202/spaghetti-bolognese.jpg"
	),
	Dish(
		3,
		"Kotlet schabowy",
		"Tradycyjne danie polskie z kotletem schabowym panierowanym w bułce tartej",
		TabType.Local,
		"https://www.kwestiasmaku.com/sites/v123.kwestiasmaku.com/files/schabowe_01.jpg"
	),
	Dish(
		4,
		"Sushi",
		"Japońska potrawa z surowym rybą i warzywami podanymi na kwaszonym ryżu",
		TabType.Abroad,
		"https://www.justonecookbook.com/wp-content/uploads/2020/01/Sushi-Rolls-Maki-Sushi-%E2%80%93-Hosomaki-1106-II.jpg"
	),
	Dish(
		5,
		"Pizza Margherita",
		"Klasyczna włoska pizza z sosem pomidorowym, mozzarellą i bazylią",
		TabType.Abroad,
		"https://upload.wikimedia.org/wikipedia/commons/c/c8/Pizza_Margherita_stu_spivack.jpg"
	),
	Dish(
		6,
		"Kebab",
		"Potrawa kuchni tureckiej z kawałkami mięsa, warzywami i sosem, podawana w bułce",
		TabType.Abroad,
		"https://ocdn.eu/pulscms-transforms/1/Ii4k9kpTURBXy82NWMxYTJhYTgzOTRiNmU0ZjIwMDIzYmQ2NjViNDA0Ni5qcGeTlQMAzL_NF-vNDXSTBc0EsM0CpJMJpjQ0YjQxYQbeAAGhMAE/domowy-kebab-z-kurczaka-to-danie-ktore-bez-problemu-przygotujecie-samodzielnie-w-domu.jpeg"
	),
	Dish(
		7,
		"Chili con carne",
		"Meksykańska potrawa z mielonym mięsem, fasolą i przyprawami",
		TabType.Abroad,
		"https://www.kwestiasmaku.com/sites/v123.kwestiasmaku.com/files/chili_con_carne_01_0.jpg"
	),
	Dish(
		8,
		"Tacos",
		"Meksykańska potrawa z mięsem, warzywami i sosem, podawana w cieście tortilli",
		TabType.Abroad,
		"https://www.thewholesomedish.com/wp-content/uploads/2019/06/The-Best-Classic-Tacos-550.jpg"
	),
	Dish(
		9,
		"Lasagne",
		"Klasyczne danie kuchni włoskiej z warstwami makaronu, mięsa i sosu beszamelowego",
		TabType.Abroad,
		"https://kristineskitchenblog.com/wp-content/uploads/2022/10/lasagna-recipe-16-2.jpg"
	),
	Dish(
		10,
		"Sałatka Cezar",
		"Klasyczna sałatka z sałatą rzymską, kurczakiem, jajkiem, parmezanem i sosem cezar",
		TabType.Abroad,
		"https://www.kwestiasmaku.com/sites/v123.kwestiasmaku.com/files/salatka_cezar_z_kurczakiem_01.jpg"
	)
)

