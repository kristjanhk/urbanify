<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <style>
        .name {
            color: #496192;
            font-size: x-large;
            font-family: 'TicketFont', serif;
        }

        .head2 {
            color: #496192;
            font-size: large;
        }

        .head3 {
            color: #000;
            font-size: medium
        }

        .paragraph {
            text-align: justify;
        }

        .toc {
            list-style-type: none;
        }

        .toc a::after {
            content: leader('.') target-counter(attr(href), page);
        }
    </style>
</head>
<body>
<h1 class="name">${event.name}</h1>
<h2 class="name">${event.date}</h2>
<h2 class="name">${event.time}</h2>

<table border="1" cellspacing="0" cellpadding="0" align="center">
    <thead>
    <tr style="background:#CCC0DA">
        <th align="center">${tickettype}</th>
        <th align="center">${ticketprice}</th>
        <th align="center">${ticketamount}</th>
        <th align="center">${tickettotal}</th>
    </tr>
    </thead>
    <tbody>
    <#list 1..event.tickettypes?size as i>
        <#if i%2==0>
        <tr style="background:#FABF8F">
        <#else>
        <tr style="background:#C4D79B">
        </#if>
        <td width="189" align="center"><p align="center">${event.tickettypes[i - 1]}</p></td>
        <td width="189" align="center"><p align="center">${event.ticketprices[i - 1]}</p></td>
        <td width="189" align="center"><p align="center">${event.ticketamounts[i - 1]}</p></td>
        <td width="189" align="center"><p align="center">${event.tickettotals[i - 1]}</p></td>
    </tr>
    </#list>
    </tbody>
</table>
<h2 class="name">${totalprice}</h2>
<h2 class="name">${totalamount}</h2>
</body>
</html>