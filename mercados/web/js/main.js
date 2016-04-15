$(function () {
    $('body').layout({
        closable: true,
        resizable: false,
        slidable: true,
        livePaneResizing: true,
        // nort
        north__slidable: false,
        north__size: 80,
        north__spacing_open: 0,
        // west
        west__slidable: false,
        west__togglerLength_closed: '100%',
        west__spacing_closed: 20,
        west__size: 216,
        west__animatePaneSizing: false,
        west__fxSpeed_size: "fast",
        west__fxSpeed_open: 1000
    });
});


