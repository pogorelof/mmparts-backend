# 1. Создание запчасти с изображениями
   *Метод: POST*

   ```URL: /parts/create```

   Описание: Создает новую запчасть и загружает изображения.

   Параметры:

В body в form-data:
- Ключ 'part' - json с деталями запчасти
- Ключ images(может быть несколько) - file изображения


   Пример JSON для part:
```json
   {
  "title": "Part",
  "description": "Description",
  "price": 123.0,
  "generations": [
    {
      "brand": "BMW",
      "model": "5-Series",
      "generation": "2010"
    }
  ]
}
```

# 2. Получение списка запчастей
   *Метод: GET*

   ```URL: /parts/```

   Описание: Возвращает список всех запчастей с возможностью фильтрации по бренду, модели и поколению.
   
Параметры запроса (Query Params):

   | Параметр	  | Тип      | 	Описание                           | 	Необязательный |
|------------|----------|-------------------------------------|-----------------|
   | brand      | 	String  | 	Фильтр по бренду	                  | Да              |
   | model      | 	String  | 	Фильтр по модели                   | 	Да             |
   | generation | 	String  | 	Фильтр по поколению	               | Да              |
  | page       | 	Integer | 	Номер страницы (для пагинации)     | 	Да             |
   | size       | 	Integer | 	Размер страницы (кол-во элементов) | 	Да             |
   | sort       | 	String  | 	Сортировка, например price,asc     | 	Да             |

   Примеры запросов в Postman:

   Получить все запчасти:
  
```` GET http://<адрес_сервера>/parts/````

   Получить все запчасти бренда Toyota:
  
``` GET http://<адрес_сервера>/parts/?brand=Toyota```

   Получить все запчасти Toyota Camry XV50, отсортированные по цене по возрастанию:
 
 ```` GET http://<адрес_сервера>/parts/?brand=Toyota&model=Camry&generation=XV50&sort=price,asc````

   Пример ответа:
````json
{
  "content": [
    {
      "id": 1,
      "title": "Part",
      "description": "Desc",
      "price": 123.0,
      "inStock": true,
      "generations": [
        {
          "id": 1,
          "brand": "BMW",
          "model": "5-Series",
          "generation": "2010"
        }
      ],
      "images": [
        {
          "id": 1,
          "url": "/uploads/1765970179810_111.jpg"
        },
        {
          "id": 2,
          "url": "/uploads/1765970179812_222.jpg"
        }
      ]
    }
  ],
  "empty": false,
  "first": true,
  "last": true,
  "number": 0,
  "numberOfElements": 1,
  "pageable": {
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 20,
    "paged": true,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "unpaged": false
  },
  "size": 20,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "totalElements": 1,
  "totalPages": 1
}
````

# 3. Получение списка брендов, моделий и поколений
1. Бренды - ```` GET http://<адрес_сервера>/generations/all/brands````
2. Модели - ```` GET http://<адрес_сервера>/generations/all/models/{brand}````
3. Поколения - ```` GET http://<адрес_сервера>/generations/all/generations/{brand}/{model}````