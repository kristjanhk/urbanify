<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <style>
        @page {background-color: #F2F2F2;}
        /*html {
            margin: 0;
            min-height: 100%;
            min-width: 100%;
            background: #EFEFEF;
        }

        body {
            margin: 0;
            min-height: 100%;
            min-width: 100;
            background: #EFEFEF;
        }*/

        .name {
            color: #262626;
            font-size: 30pt;
            font-family: 'TicketFont', serif;
            line-height: 25%;
        }

        .date {
            color: #5b5c5c;
            font-size: 20pt;
            font-family: 'TicketFont', serif;
        }

        .bottom {
            color: #262626;
            font-size: 22pt;
            text-align: right;
            background: #C6C6C5;
            line-height: 10%;
            font-family: 'TicketFont', serif;
        }

        .tablehead {
            color: #262626;
            font-size: 18pt;
            font-style: normal;
            text-align: right;
            background: #C6C6C5;
            font-family: 'TicketFont', serif;
            line-height: 30pt;
        }

        .content {
            color: #262626;
            font-size: 14pt;
            text-align: right;
            font-family: 'TicketFont', serif;
        }

        .toc a::after {
            content: leader('.') target-counter(attr(href), page);
        }
    </style>
</head>
<body>
<h1 class="name">${event.name}</h1>
<h2 class="date">${event.date} ${event.time}</h2>
<h2 class="date">${logo} ${companyname}</h2>
<h2 class="date">${reportnr} ${workername}</h2>

<table border="0" cellspacing="0" align="center">
    <thead>
    <tr class="tablehead">
        <th align="center">${tickettype}</th>
        <th align="center">${ticketprice}</th>
        <th align="center">${ticketamount}</th>
        <th align="center">${tickettotal}</th>
    </tr>
    </thead>
    <tbody>
    <#list 1..event.tickettypes?size as i>
        <#if i%2==0>
        <tr class="content" style="background:#EAEAEA">
        <#else>
        <tr class="content" style="background:#D8D8D8">
        </#if>
        <td width="189" align="center"><p style="content" align="center">${event.tickettypes[i - 1]}</p></td>
        <td width="189" align="center"><p style="content" align="center">${event.ticketprices[i - 1]}</p></td>
        <td width="189" align="center"><p style="content" align="center">${event.ticketamounts[i - 1]}</p></td>
        <td width="189" align="center"><p style="content" align="center">${event.tickettotals[i - 1]}</p></td>
    </tr>
    </#list>
    <tr >
        <td class="bottom" height="2" width="189" align="center"><p align="center"></p></td>
        <td class="bottom" height="2" width="189" align="center"><p align="center">${tickettotal}</p></td>
        <td class="bottom" height="2" width="189" align="center"><p align="center">${totalamount}</p></td>
        <td class="bottom" height="2" width="189" align="center"><p align="center">${totalprice}</p></td>
    </tr>
    </tbody>
</table>
</body>
</html>