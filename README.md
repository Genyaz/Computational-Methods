# Computational-Methods

Сюда будут выкладываться материалы, связанные с дисциплиной ["Численные методы"](http://neerc.ifmo.ru/wiki/index.php?title=%D0%A3%D1%87%D0%B0%D1%81%D1%82%D0%BD%D0%B8%D0%BA:Dgerasimov/%D0%A7%D0%B8%D1%81%D0%BB%D0%B5%D0%BD%D0%BD%D1%8B%D0%B5_%D0%BC%D0%B5%D1%82%D0%BE%D0%B4%D1%8B)

## Homework 1

В этом задании нужно проверить работу нескольких численных методов при моделировании одномерного уравнения теплопроводности.
Результатом является интерактивная программа, позволяющая менять константы уравнения, численный метод его решения, представленный разностной схемой, константы самого численного метода.

Модельное уравнение: dT/dt + u * dT/dx - chi * d^2T/dx^2 = 0

Разностные схемы:
* Явная по потоку (ecf)
* Явная против потока (ebf)
* Явная центральная (ec)
* Неявная по потоку (icf)
* Неявная против потока (ibf)
* Неявная центральная (ic)
* (Диффорте-Франкла?) ("чехарда") (lf)

Начальные условия (T0(x_i) - начальная температура в i-ой пространственной точке):
* Ступенька (T0(x_1) = T0(x_2) = .. = T0(x_i) = 1 != T0(x_i+1) = 0 = T0(x_i+2) = ... = T0(x_n))
* Единичный импульс (T0(x_1) = T0(x_2) = ... = T0(x_i-1) = 0 != T0(x_i) = 1 != T0(x_i+1) = 0 = ... = T0(x_n))

Граничные условия:
* Температура на левой и правой границе является константой по времени.

Описание программы:

Пользователь взаимодействует с программой через консольный интерфейс. Допустимы команды:
* set \<key\> \<value\> - устанавливает значение переменной
* get \<key\> - получает значение переменной
* getall - выводит все доступные переменные
* call \<callback\> - запускает программу (синхронно)

Используемые переменные:
* init - начальное распределение, может быть "edge" (ступенька), "splash" (импульс), "prev" (конечный результат предыдущего запуска)
* dx - пространственный шаг вычисления
* dt - временной шаг вычисления
* u, chi - параметры уравнения
* s, r - число Куранта, сеточное число Рейнольдса, позволяют задать соответствющие им u, chi
* n - число пространственных шагов
* iterations - число итераций по времени
* method - используемый метод, соответствующие константы приведены выше
* interval - задержка показа следующего кадра при визуализации

Программы, запускаемые call:
* fromrs - подставляет u, chi на основе r, s
* calculate - запускает вычисления и визуализирует их
