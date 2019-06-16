"use strict";

function _toConsumableArray(arr) { return _arrayWithoutHoles(arr) || _iterableToArray(arr) || _nonIterableSpread(); }

function _nonIterableSpread() { throw new TypeError("Invalid attempt to spread non-iterable instance"); }

function _iterableToArray(iter) { if (Symbol.iterator in Object(iter) || Object.prototype.toString.call(iter) === "[object Arguments]") return Array.from(iter); }

function _arrayWithoutHoles(arr) { if (Array.isArray(arr)) { for (var i = 0, arr2 = new Array(arr.length); i < arr.length; i++) { arr2[i] = arr[i]; } return arr2; } }

function _instanceof(left, right) { if (right != null && typeof Symbol !== "undefined" && right[Symbol.hasInstance]) { return right[Symbol.hasInstance](left); } else { return left instanceof right; } }

function _typeof(obj) { if (typeof Symbol === "function" && typeof Symbol.iterator === "symbol") { _typeof = function _typeof(obj) { return typeof obj; }; } else { _typeof = function _typeof(obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; }; } return _typeof(obj); }

function _classCallCheck(instance, Constructor) { if (!_instanceof(instance, Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function _createClass(Constructor, protoProps, staticProps) { if (protoProps) _defineProperties(Constructor.prototype, protoProps); if (staticProps) _defineProperties(Constructor, staticProps); return Constructor; }

function _possibleConstructorReturn(self, call) { if (call && (_typeof(call) === "object" || typeof call === "function")) { return call; } return _assertThisInitialized(self); }

function _assertThisInitialized(self) { if (self === void 0) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return self; }

function _getPrototypeOf(o) { _getPrototypeOf = Object.setPrototypeOf ? Object.getPrototypeOf : function _getPrototypeOf(o) { return o.__proto__ || Object.getPrototypeOf(o); }; return _getPrototypeOf(o); }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function"); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, writable: true, configurable: true } }); if (superClass) _setPrototypeOf(subClass, superClass); }

function _setPrototypeOf(o, p) { _setPrototypeOf = Object.setPrototypeOf || function _setPrototypeOf(o, p) { o.__proto__ = p; return o; }; return _setPrototypeOf(o, p); }

var Bar =
    /*#__PURE__*/
    function (_React$Component) {
        _inherits(Bar, _React$Component);

        function Bar() {
            _classCallCheck(this, Bar);

            return _possibleConstructorReturn(this, _getPrototypeOf(Bar).apply(this, arguments));
        }

        _createClass(Bar, [{
            key: "render",
            value: function render() {
                return React.createElement("td", {
                    className: "bar",
                    style: {
                        width: (this.props.milliseconds <= 0 ? 0 : Math.floor(200 * (this.props.milliseconds / this.props.maxMilliseconds))) + "px"
                    }
                });
            }
        }]);

        return Bar;
    }(React.Component);

var Milliseconds =
    /*#__PURE__*/
    function (_React$Component2) {
        _inherits(Milliseconds, _React$Component2);

        function Milliseconds() {
            _classCallCheck(this, Milliseconds);

            return _possibleConstructorReturn(this, _getPrototypeOf(Milliseconds).apply(this, arguments));
        }

        _createClass(Milliseconds, [{
            key: "render",
            value: function render() {
                return React.createElement("td", {
                    className: "milliseconds"
                }, this.props.milliseconds == 0 ? "..." : this.props.milliseconds < 0 ? -this.props.milliseconds + "/3" : this.props.milliseconds + " ms");
            }
        }]);

        return Milliseconds;
    }(React.Component);

var Name =
    /*#__PURE__*/
    function (_React$Component3) {
        _inherits(Name, _React$Component3);

        function Name() {
            _classCallCheck(this, Name);

            return _possibleConstructorReturn(this, _getPrototypeOf(Name).apply(this, arguments));
        }

        _createClass(Name, [{
            key: "render",
            value: function render() {
                return React.createElement("td", {
                    className: "name"
                }, this.props.locationName);
            }
        }]);

        return Name;
    }(React.Component);

var Line =
    /*#__PURE__*/
    function (_React$Component4) {
        _inherits(Line, _React$Component4);

        function Line(props) {
            _classCallCheck(this, Line);

            return _possibleConstructorReturn(this, _getPrototypeOf(Line).call(this, props));
        }

        _createClass(Line, [{
            key: "render",
            value: function render() {
                return React.createElement("tr", {
                    className: "line"
                }, React.createElement(Name, {
                    locationName: this.props.name,
                    provider: this.props.provider,
                    url: this.props.url,
                    key: this.props.index
                }), React.createElement(Milliseconds, {
                    milliseconds: this.props.milliseconds
                }));
            }
        }]);

        return Line;
    }(React.Component);

var List =
    /*#__PURE__*/
    function (_React$Component5) {
        _inherits(List, _React$Component5);

        function List(props) {
            var _this;

            _classCallCheck(this, List);

            _this = _possibleConstructorReturn(this, _getPrototypeOf(List).call(this, props));
            _this.state = {
                millisecondsOfTargets: new Array(),
                rankList: new Array()
            };
            _this.data = {
                img: document.createElement("img"),
                testTimeOut: false,
                timeOutId: null,
                currentIndex: 0,
                countOfLoad: 0,
                startTime: 0,
                endTime: 0,
                minOfCurrentTarget: 60000
            };
            document.body.appendChild(_this.data.img);
            _this.lineRef = new Array();

            for (var i = 0; i <= Object.keys(_this.props.targets).length - 1; i++) {
                _this.lineRef[i] = React.createRef();
            }

            for (var _i = 0; _i <= Object.keys(_this.props.targets).length - 1; _i++) {
                _this.state.millisecondsOfTargets[_i] = 0;
            }

            for (var _i2 = 0; _i2 <= Object.keys(_this.props.targets).length - 1; _i2++) {
                _this.state.rankList[_i2] = {
                    id: _i2,
                    rank: _i2,
                    milliseconds: 60000 + _i2
                };
            }

            _this.onTimeOut = _this.onTimeOut.bind(_assertThisInitialized(_this));
            _this.onLoaded = _this.onLoaded.bind(_assertThisInitialized(_this));
            return _this;
        }

        _createClass(List, [{
            key: "updateCount",
            value: function updateCount() {
                var newMillisecondsOfTargets = this.state.millisecondsOfTargets.slice();
                newMillisecondsOfTargets[this.data.currentIndex] = -this.data.countOfLoad;
                this.setState({
                    millisecondsOfTargets: newMillisecondsOfTargets
                });
            }
        }, {
            key: "onTimeOut",
            value: function onTimeOut() {
                var callback = this.data.img.onerror;
                this.data.img.onerror = null;
                this.data.img.src = "";
                setTimeout(callback, 1);
            }
        }, {
            key: "clearThisTimeOut",
            value: function clearThisTimeOut() {
                try {
                    clearTimeout(this.data.timeOutId);
                } catch (e) {
                    console.log("clearThisTimeOut ERROR");
                }
            }
        }, {
            key: "prepareTimeOut",
            value: function prepareTimeOut() {
                this.clearThisTimeOut();
                this.data.timeOutId = setTimeout(this.onTimeOut, 6000);
            }
        }, {
            key: "doNextTest",
            value: function doNextTest() {
                var currentLine = this.lineRef[this.data.currentIndex];
                this.data.countOfLoad++;
                this.updateCount();
                this.data.startTime = new Date().getTime();
                this.data.img.onerror = this.onLoaded;
                this.data.img.style = "display:none;";
                this.prepareTimeOut();

                if (!this.data.testTimeOut) {
                    this.data.img.src = currentLine.current.props.url + "" + Math.random();
                }
            }
        }, {
            key: "doNextTarget",
            value: function doNextTarget() {
                this.data.currentIndex++;

                if (this.data.currentIndex > this.lineRef.length - 1) {
                    this.props.providerFinished();
                    this.clearThisTimeOut();
                    return;
                }

                var currentLine = this.lineRef[this.data.currentIndex];
                this.data.countOfLoad = 1;
                this.updateCount();
                this.data.startTime = new Date().getTime();
                this.data.img.onerror = this.onLoaded;
                this.data.img.style = "display:none;";
                this.prepareTimeOut();

                if (!this.data.testTimeOut) {
                    this.data.img.src = currentLine.current.props.url + "" + Math.random();
                }
            }
        }, {
            key: "onLoaded",
            value: function onLoaded() {
                if (this.data.countOfLoad == 1) {
                    this.doNextTest();
                    return;
                }

                this.data.endTime = new Date().getTime();
                var currentCostOfTime = this.data.endTime - this.data.startTime;

                if (currentCostOfTime < this.data.minOfCurrentTarget) {
                    this.data.minOfCurrentTarget = currentCostOfTime;
                }

                if (this.data.countOfLoad == 3) {
                    var newMillisecondsOfTargets = this.state.millisecondsOfTargets.slice();
                    newMillisecondsOfTargets[this.data.currentIndex] = this.data.minOfCurrentTarget;
                    this.setState({
                        millisecondsOfTargets: newMillisecondsOfTargets
                    });
                    var newRankListWithNewData = JSON.parse(JSON.stringify(this.state.rankList));
                    newRankListWithNewData[this.data.currentIndex].milliseconds = this.data.minOfCurrentTarget;
                    var newRankListSort = JSON.parse(JSON.stringify(newRankListWithNewData));
                    newRankListSort.sort(function (a, b) {
                        return a.milliseconds - b.milliseconds;
                    });
                    var newRankListForUpdate = JSON.parse(JSON.stringify(newRankListWithNewData));

                    for (var i = 0; i <= newRankListForUpdate.length - 1; i++) {
                        var currentId = newRankListForUpdate[i].id;

                        for (var j = 0; j <= newRankListSort.length; j++) {
                            if (currentId == newRankListSort[j].id) {
                                newRankListForUpdate[i].rank = j;
                                break;
                            }
                        }
                    }

                    this.setState({
                        rankList: newRankListForUpdate
                    });
                    this.data.minOfCurrentTarget = 60000;
                    this.doNextTarget();
                    return;
                }

                this.doNextTest();
            }
        }, {
            key: "componentDidMount",
            value: function componentDidMount() {
                var currentLine = this.lineRef[this.data.currentIndex];
                this.data.countOfLoad = 1;
                this.updateCount();
                this.data.startTime = new Date().getTime();
                this.data.img.onerror = this.onLoaded;
                this.prepareTimeOut();

                if (!this.data.testTimeOut) {
                    this.data.img.src = currentLine.current.props.url + Math.random();
                }
            }
        }, {
            key: "render",
            value: function render() {
                return React.createElement("tbody", {
                    className: "list"
                }, this.props.targets.map(function (item, index) {
                    return React.createElement(Line, {
                        name: item.name,
                        key: index,
                        url: item.url,
                        milliseconds: this.state.millisecondsOfTargets[index],
                        maxMilliseconds: Math.max.apply(Math, _toConsumableArray(this.state.millisecondsOfTargets)),
                        index: this.state.rankList[index].rank,
                        number: this.state.rankList.length,
                        provider: this.props.provider,
                        showNotice: this.props.showNotice,
                        ref: this.lineRef[index]
                    });
                }.bind(this)));
            }
        }]);

        return List;
    }(React.Component);

var App =
    /*#__PURE__*/
    function (_React$Component6) {
        _inherits(App, _React$Component6);

        function App(props) {
            var _this2;

            _classCallCheck(this, App);

            _this2 = _possibleConstructorReturn(this, _getPrototypeOf(App).call(this, props));
            _this2.state = {
                allData: data,
                messageVisible: false,
                noticeVisible: false,
                clickedProvider: "",
                clickedName: "",
                clickedServer: "",
                clickedDownload: ""
            };
            _this2.data = {
                countOfFinishedProviders: 0
            };
            _this2.providerFinished = _this2.providerFinished.bind(_assertThisInitialized(_this2));
            _this2.allProvidersFinished = _this2.allProvidersFinished.bind(_assertThisInitialized(_this2));
            return _this2;
        }

        _createClass(App, [{
            key: "providerFinished",
            value: function providerFinished() {
                this.data.countOfFinishedProviders++;
            }
        }, {
            key: "allProvidersFinished",
            value: function allProvidersFinished() {
                return this.data.countOfFinishedProviders == Object.keys(this.state.allData).length;
            }
        }, {
            key: "render",
            value: function render() {
                return React.createElement("div", null, Object.keys(this.state.allData).map(function (item, index) {
                    var sectionStyle = {
                        // height: this.state.allData[item].length * (18 + 2) + 30 + "px",
                        "marginBottom": "0px"
                    };
                    return React.createElement("table", {
                        className: "table",
                        style: sectionStyle
                    }, React.createElement("thead", null, React.createElement("tr", null, React.createElement("th", null, "Location"), React.createElement("th", null, "\u5EF6\u8FDF\u6D4B\u8BD5"))), React.createElement(List, {
                        provider: item,
                        providerFinished: this.providerFinished,
                        targets: this.state.allData[item]
                    }));
                }.bind(this)));
            }
        }]);

        return App;
    }(React.Component);

function main() {
    ReactDOM.render(React.createElement(App, null), document.getElementById("result"));
}