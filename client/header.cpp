#include "header.h"
#include "qstring.h"
#include "QObject"
#include <string>

 Header::Header()
{
}

  std::string Header::getHead(int length) {
     if (length > 9999) return "9999";
     if (length <= 0) return "0000";
     std::string result = "0000";
     int i = 3;
     while(length > 0) {
         result[i] = '0' + length % 10;
         length = length / 10;
         i--;
     }
     return result;
 }
