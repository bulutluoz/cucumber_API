Feature:
  @Api
  Scenario:

    Given Kullanici "jPHBaseUrl" base URL'ini kullanir
    Then Path parametreleri icin "posts/44" kullanir
    And jPH server a GET request gonderir ve testleri yapmak icin response degerini kaydeder
    Then jPH respons'da status degerinin 200
    And jPH respons'da content type degerinin "application/json; charset=utf-8"
    Then jPH GET respons body'sinde "userId" degerinin Integer 5
    And jPH GET respons body'sinde "title" degerinin String "optio dolor molestias sit"




