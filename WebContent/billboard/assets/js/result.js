import { getSearch } from "./assets/js/get-search.js";
const useState = React.useState;
const useEffect = React.useEffect;

const Result = () => {
  const [musics, setMusics] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
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
        {!error && !success && (
          <>
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
              <a className="back-to-search" href="./search.html">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="icon icon-tabler icon-tabler-arrow-back"
                  width="20"
                  height="20"
                  viewBox="0 0 24 24"
                  stroke-width="1.5"
                  stroke="#2c3e50"
                  fill="none"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                >
                  <path stroke="none" d="M0 0h24v24H0z" fill="none" />
                  <path d="M9 11l-4 4l4 4m-4 -4h11a4 4 0 0 0 0 -8h-1" />
                </svg>
                <span>検索画面へ戻る</span>
              </a>
            </div>
          </>
        )}
        {error && !success ? <Error error={error} /> : null}
        {success && !error ? <Success success={success} /> : null}
      </div>
    </>
  );
};

const Success = ({ success }) => (
  <div className="error-container">
    <svg
      xmlns="http://www.w3.org/2000/svg"
      class="icon icon-tabler icon-tabler-circle-check"
      width="44"
      height="44"
      viewBox="0 0 24 24"
      stroke-width="1.5"
      stroke="#2c3e50"
      fill="none"
      stroke-linecap="round"
      stroke-linejoin="round"
    >
      <path stroke="none" d="M0 0h24v24H0z" fill="none" />
      <circle cx="12" cy="12" r="9" />
      <path d="M9 12l2 2l4 -4" />
    </svg>
    <p className="error-message">{success}</p>
    <a className="link-with-icon" onClick={() => history.back()}>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        class="icon icon-tabler icon-tabler-arrow-back"
        width="20"
        height="20"
        viewBox="0 0 24 24"
        stroke-width="1.5"
        stroke="#2c3e50"
        fill="none"
        stroke-linecap="round"
        stroke-linejoin="round"
      >
        <path stroke="none" d="M0 0h24v24H0z" fill="none" />
        <path d="M9 11l-4 4l4 4m-4 -4h11a4 4 0 0 0 0 -8h-1" />
      </svg>
      <span>戻る</span>
    </a>
  </div>
);

const Error = ({ error }) => (
  <div className="error-container">
    <svg
      xmlns="http://www.w3.org/2000/svg"
      class="icon icon-tabler icon-tabler-alert-triangle"
      width="44"
      height="44"
      viewBox="0 0 24 24"
      stroke-width="1.5"
      stroke="#2c3e50"
      fill="none"
      stroke-linecap="round"
      stroke-linejoin="round"
    >
      <path stroke="none" d="M0 0h24v24H0z" fill="none" />
      <path d="M12 9v2m0 4v.01" />
      <path d="M5 19h14a2 2 0 0 0 1.84 -2.75l-7.1 -12.25a2 2 0 0 0 -3.5 0l-7.1 12.25a2 2 0 0 0 1.75 2.75" />
    </svg>
    <p className="error-message">{error}</p>
    <a className="link-with-icon" onClick={() => history.back()}>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        class="icon icon-tabler icon-tabler-arrow-back"
        width="20"
        height="20"
        viewBox="0 0 24 24"
        stroke-width="1.5"
        stroke="#2c3e50"
        fill="none"
        stroke-linecap="round"
        stroke-linejoin="round"
      >
        <path stroke="none" d="M0 0h24v24H0z" fill="none" />
        <path d="M9 11l-4 4l4 4m-4 -4h11a4 4 0 0 0 0 -8h-1" />
      </svg>
      <span>戻る</span>
    </a>
  </div>
);

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
