# Computational-Methods

Сюда будут выкладываться материалы, связанные с дисциплиной "Численные методы"

## Homework 1

В этом задании нужно проверить работу нескольких численных методов при моделировании одномерного уравнения теплопроводности.
Результатом является интерактивная программа, позволяющая менять константы уравнения, численный метод его решения, представленный разностной схемой, константы самого численного метода.

Модельное уравнение: dT/dt + u * dT/dx + chi * d^2T/dx^2 = 0

Разностные схемы:
* Явная по потоку
* Явная против потока
* Явная центральный
* Неявная по потоку
* Неявная против потока
* Неявная центральная
* (Диффорте-Франкла?) ("чехарда")

Начальные условия:
* Ступенька (x_1 = x_2 = .. = x_i = 1 != x_i+1 = 0 = x_i+2 = ... = x_n)
* Единичный импульс (x_1 = x_2 = ... = x_i-1 = 0 != x_i = 1 != x_i+1 = 0 = ... = x_n)

Граничные условия:
* Левая и правая границы являются константами по времени.
