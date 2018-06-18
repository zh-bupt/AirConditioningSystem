#-------------------------------------------------
#
# Project created by QtCreator 2018-06-12T20:50:08
#
#-------------------------------------------------

QT       += core gui
QT       += network
greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = clients
TEMPLATE = app


SOURCES += main.cpp\
        mainwindow.cpp \
    login.cpp \
    header.cpp \
    socket.cpp

HEADERS  += mainwindow.h \
    login.h \
    header.h \
    header.h \
    socket.h

FORMS    += mainwindow.ui \
    login.ui
