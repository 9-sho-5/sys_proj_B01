import { getSearch } from "./assets/js/get-search.js";
const useState = React.useState;
const useEffect = React.useEffect;

const Result = () => {
  const [musics, setMusics] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");
  const url = new URL(window.location.href);
  console.log(url);
  const params = url.searchParams;
  // パラメータからスペースを除去
  const value = params.get("value").replace(/\s+/g, "");

  useEffect(() => {
    (async () => {
      setIsLoading(true);
      if (!value) {
        setError("キーワードを入力してください");
        setIsLoading(false);
      } else {
        const searchResult = await getSearch(value);
        searchResult
          ? setMusics(searchResult.data)
          : setError("楽曲が見つかりませんでした");
        setIsLoading(false);
      }
    })();
  }, []);

  return (
    <>
      <Header />
      <div className="page-container">
        <h1>{value} の検索結果</h1>
        <div className="grid-container">
          {musics && musics.map((music) => <Track music={music} />)}
        </div>
        <div>
          {isLoading && (
            <>
              <p className="loader">Loading...</p>
            </>
          )}
          {error && <p>{error}</p>}
          <a href="./search.html">検索画面へ戻る</a>
        </div>
      </div>
    </>
  );
};

const Track = ({ music }) => {
  return (
    <div className="track">
      <div className="track-image-container">
        <img
          src={music.album_image_url}
          alt={music.album_name}
          className="track-image"
        />
        <button className="request-button">リクエスト</button>
      </div>
      <p title={music.track_name} className="track-name">
        <a>{music.track_name}</a>
      </p>
      <span title={music.artist_name} className="artist-name">
        {music.artist_name}
      </span>
    </div>
  );
};

const Header = () => {
  return (
    <header>
      <a href="./index.html" className="logo">
        Billboard Kindai
      </a>
      <nav>
        <ul className="nav-list">
          <li className="nav-list-item">
            <a href="./index.html">ホーム</a>
          </li>
          <li clasName="nav-list-item">
            <a href="./search.html">検索</a>
          </li>
        </ul>
      </nav>
    </header>
  );
};

const container = document.getElementById("root");
const root = ReactDOM.createRoot(container);
root.render(<Result />);
